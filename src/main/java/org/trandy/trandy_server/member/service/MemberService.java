package org.trandy.trandy_server.member.service;

import org.trandy.trandy_server.common.Constants;
import org.trandy.trandy_server.common.ResponseDto;
import org.trandy.trandy_server.common.jwt.JwtTokenProvider;
import org.trandy.trandy_server.common.jwt.enums.TokenType;
import org.trandy.trandy_server.common.redis.RedisRepository;
import org.trandy.trandy_server.common.redis.RefreshToken;
import org.trandy.trandy_server.exception.CustomException;
import org.trandy.trandy_server.exception.ExceptionStatus;
import org.trandy.trandy_server.member.domain.Member;
import org.trandy.trandy_server.member.domain.converter.MemberConverter;
import org.trandy.trandy_server.member.domain.dto.request.MemberAuthRequest;
import org.trandy.trandy_server.member.domain.dto.request.MemberCreateRequest;
import org.trandy.trandy_server.member.domain.dto.request.MemberUpdateRequest;
import org.trandy.trandy_server.member.repository.MemberRepository;
import org.trandy.trandy_server.util.Log;
import org.trandy.trandy_server.util.PasswordEncoderUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberConverter memberConverter;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisRepository redisRepository;

    @Transactional
    public ResponseDto signup(MemberCreateRequest request) {
        memberRepository.findByMemberIdAndDeletedFalse(request.getMemberId()).ifPresent(member -> {
            throw new CustomException(ExceptionStatus.MemberIdDuplicatedException);
        });

        memberRepository.save(memberConverter.convertDtoToEntity(request));

        return ResponseDto.success(Constants.API_RESPONSE_SUCCESSED);
    }


    @Transactional(readOnly = true)
    public ResponseDto find() {

        List<Member> members = memberRepository.queryFindAllMember();

        if(members == null || members.size() == 0){
            return ResponseDto.success("존재하는 유저가 없습니다.");
        }

        // 컬렉션 내부 타입 변환
        return ResponseDto.success(members.stream()
                .map(memberConverter::convertEntityToDto)
                .collect(Collectors.toList()));
    }

    @Transactional(readOnly = true)
    public ResponseDto login(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             MemberAuthRequest request) {
        Member member = findByMemberIdAndDeletedFalse(request.getMemberId());

        // password Check
        PasswordEncoderUtil.checkPassword(request.getPassword(), member.getPassword());

        // Create AccessToken & RefreshToken
        setAccessTokenToHttpHeader(member, httpServletResponse);
        setRefreshTokenToHttpHeader(member, httpServletRequest, httpServletResponse);

        return ResponseDto.success(Constants.API_RESPONSE_SUCCESSED);
    }

    public void setAccessTokenToHttpHeader(Member member,
                                           HttpServletResponse response){
        try{
            String accessToken = jwtTokenProvider.createToken(member.getMemberId(), member.getMemberRole(), TokenType.ACCESS);

            System.out.println(accessToken);

            ResponseCookie cookie = ResponseCookie.from(
                    Constants.AUTHORIZATION_HEADER,
                    URLEncoder.encode(accessToken, "UTF-8"))
                            .path("/")
                    .httpOnly(true)
                    .sameSite("None")
                    .secure(true)
                    .maxAge(Constants.ACCESS_TOKEN_TIME)
                    .build();
                response.addHeader("Set-Cookie", cookie.toString());

        }catch (UnsupportedEncodingException e){
            throw new CustomException(ExceptionStatus.AccessTokenCreateFailedException);
        }
    }

    public void setRefreshTokenToHttpHeader(Member member,
                                            HttpServletRequest request,
                                            HttpServletResponse response){
        try {
            String refreshToken = jwtTokenProvider.createToken(member.getEmail(), member.getMemberRole(), TokenType.REFRESH);

            ResponseCookie cookie = ResponseCookie.from(
                            Constants.REFRESH_HEADER,
                            URLEncoder.encode(refreshToken, "UTF-8"))
                    .path("/")
//                    .httpOnly(true)
                    .sameSite("None")
                    .secure(true)
                    .maxAge(Constants.REFRESH_TOKEN_TIME)
                    .build();
            response.addHeader("Set-Cookie", cookie.toString());

            // 기존 저장된 RFT가 있는지 검색 후 있으면 만료 처리
            Optional<RefreshToken> findRefreshToken = redisRepository.findById(member.getEmail());
            long expiration = Constants.REFRESH_TOKEN_TIME / 1000;

            if(findRefreshToken.isPresent()){
                RefreshToken updatedRefreshToken = findRefreshToken.get().updateToken(refreshToken, expiration);
                redisRepository.save(updatedRefreshToken);
            } else {
                // 기존 RFT가 없을 경우 생성된 RFT로 저장
                RefreshToken refreshTokenToSave = RefreshToken.builder()
                        .email(member.getEmail())
                        .refreshToken(refreshToken)
                        .expiration(expiration)
                        .build();
                redisRepository.save(refreshTokenToSave);
            }

        }catch (UnsupportedEncodingException e){
            throw new CustomException(ExceptionStatus.RefreshTokenCreateFailedException);
        }
    }

    @Transactional
    public ResponseDto update(MemberUpdateRequest request) {
        Member member = findByMemberIdAndDeletedFalse(request.getMemberId());

        // password Check
        PasswordEncoderUtil.checkPassword(request.getPassword(), member.getPassword());

        member.update(request);

        memberRepository.save(member);

        return ResponseDto.success(Constants.API_RESPONSE_SUCCESSED);
    }

    @Transactional
    public ResponseDto delete(MemberAuthRequest request) {
        Member member = memberRepository.findByMemberId(request.getMemberId())
                .orElseThrow(() -> new CustomException(ExceptionStatus.MemberNotFoundException));

        // password Check
        PasswordEncoderUtil.checkPassword(request.getPassword(), member.getPassword());

        member.delete(member.getDeleted());

        return ResponseDto.success(Constants.API_RESPONSE_SUCCESSED);
    }

    public Member findByMemberIdAndDeletedFalse(String memberId){
        Member member = memberRepository.findByMemberIdAndDeletedFalse(memberId)
                .orElseThrow(() -> new CustomException(ExceptionStatus.MemberNotFoundException));

        return member;
    }

    public ResponseDto logout(Member member,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        deleteAllCookies(request, response);
        deleteRefreshToken(member.getEmail());
        return ResponseDto.success(Constants.API_RESPONSE_SUCCESSED);
    }

    public void deleteAllCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                log.info(cookie.getName() + " " + cookie.getValue() + " " + cookie.getMaxAge());
                ResponseCookie responseCookie = ResponseCookie.from(cookie.getName(), null).
                        path("/").
//                        httpOnly(true).
        sameSite("None").
                        secure(true).
                        maxAge(1).
                        build();
                response.addHeader("Set-Cookie", responseCookie.toString());
            }
        }
    }
    public void deleteRefreshToken(String email) {
        Optional<RefreshToken> refreshToken = redisRepository.findById(email);
        if(refreshToken.isPresent()){
            redisRepository.deleteById(email);
        }
    }

    public ResponseDto reIssueAccessToken(Member member, HttpServletResponse response) {
        RefreshToken refreshToken = redisRepository.findById(member.getEmail()).orElseThrow(
                () -> new CustomException(ExceptionStatus.RefreshTokenNotFoundException));

        Log.varLog("email" ,refreshToken.getEmail());

        setAccessTokenToHttpHeader(member, response);

        return ResponseDto.success(Constants.API_RESPONSE_SUCCESSED);
    }
}
