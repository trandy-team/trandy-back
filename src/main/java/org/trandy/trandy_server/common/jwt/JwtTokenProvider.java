package org.trandy.trandy_server.common.jwt;

import org.trandy.trandy_server.common.Constants;
import org.trandy.trandy_server.common.jwt.enums.TokenState;
import org.trandy.trandy_server.common.jwt.enums.TokenType;
import org.trandy.trandy_server.common.redis.RedisRepository;
import org.trandy.trandy_server.common.redis.RefreshToken;
import org.trandy.trandy_server.exception.ExceptionStatus;
import org.trandy.trandy_server.member.domain.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final UserDetailsServiceImpl userDetailsService;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private final RedisRepository redisRepository;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.secret.key}")
    private String secretKey;

    private Key key;

    //
    @PostConstruct
    public void init(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(String email, Role role, TokenType tokenType){
        Date date = new Date();
        long time = tokenType == TokenType.ACCESS ? Constants.ACCESS_TOKEN_TIME : Constants.REFRESH_TOKEN_TIME;

        return Constants.Bearer_PREFIX +
                Jwts.builder()
                        .setSubject(email)
                        .claim(Constants.AUTHORIZATION_KEY, role)
                        .setExpiration(new Date(date.getTime() + time))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    public String resolveToken(Cookie cookie) throws UnsupportedEncodingException {
        String bearerToken = URLDecoder.decode(cookie.getValue(), "UTF-8");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(Constants.Bearer_PREFIX)){
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰을 해제하여 검증한다. 파싱된 정보는 Claims에 저장된다.
    public TokenState validateToken(String accessToken){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken);
            return TokenState.VALID;
        } catch (SecurityException | MalformedJwtException e) {
            throw new MalformedJwtException(ExceptionStatus.TokenSecurityExceptionOrMalformedJwtException.getErrorMessage());
        } catch (ExpiredJwtException e) {
            return TokenState.EXPIRED;
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException(ExceptionStatus.TokenUnsupportedJwtException.getErrorMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ExceptionStatus.TokenIllegalArgumentException.getErrorMessage());
        }
    }

    public Boolean validateRefreshToken(String refreshToken){
        if(validateToken(refreshToken) != TokenState.VALID){
            return false;
        }

        Optional<RefreshToken> savedRefreshToken = redisRepository.findById(getUserInfoFromToken(refreshToken).getSubject());

        return savedRefreshToken.isPresent() && refreshToken.equals(savedRefreshToken.get().getRefreshToken().substring(7));
    }

    // 토큰 해제 후 유저 정보가 저장된 Claims 가져오기
    public Claims getUserInfoFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    // 유저 정보가 들어간 인증 객체 생성
    public Authentication createAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByEmail(email);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
