package org.trandy.trandy_server.member.controller;

import org.trandy.trandy_server.common.ResponseDto;
import org.trandy.trandy_server.common.jwt.UserDetailsImpl;
import org.trandy.trandy_server.member.domain.dto.request.MemberAuthRequest;
import org.trandy.trandy_server.member.domain.dto.request.MemberCreateRequest;
import org.trandy.trandy_server.member.domain.dto.request.MemberUpdateRequest;
import org.trandy.trandy_server.member.service.MemberService;
import org.trandy.trandy_server.util.Log;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 로그인 관련에서만 HttpServletRequest request, HttpServletResponse response 사용.
    // form Data 등 URL 상의 정보를 받아올 떈 RequestParam, 그 외는 모두 RequestBody를 사용한다. -> 객체에 담음.

    @Operation(summary = "유저 회원가입", description = "유저 회원가입 후, DB에 등록")
    @ApiResponse(responseCode = "200", description = "SUCCESSED",
            content = @Content(schema = @Schema(implementation = MemberCreateRequest.class)))
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(@Valid @RequestBody final MemberCreateRequest request) {

        Log.objectInfo(request);

        return ResponseEntity.ok(memberService.signup(request));
    }

    @Operation(summary = "유저 로그인", description = "로그인 성공 시 인증 토큰 발급하여 반환")
    @ApiResponse(responseCode = "200", description = "SUCCESSED")
    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(HttpServletRequest httpServletRequest,
                                             HttpServletResponse httpServletResponse,
                                             @RequestBody final MemberAuthRequest request) {

        Log.objectInfo(request);

        return ResponseEntity.ok(memberService.login(httpServletRequest, httpServletResponse, request));
    }

    @Operation(summary = "토큰 재발급", description = "Refresh Token 을 보내줘야 합니다.")
    @PostMapping("/reissue") // access token이 만료됐을 경우
    public ResponseEntity<ResponseDto> reIssueAccessToken(@Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                          HttpServletResponse httpServletResponse) {

        return ResponseEntity.ok(memberService.reIssueAccessToken(userDetails.getMember(), httpServletResponse));
    }

    @Operation(summary = "유저 로그아웃", description = "로그아웃 시, 리프레시 토큰 삭제")
    @ApiResponse(responseCode = "200", description = "SUCCESSED")
    @PostMapping("/logout")
    public ResponseEntity<ResponseDto> logout(HttpServletRequest httpServletRequest,
                                              HttpServletResponse httpServletResponse,
                                              @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok(memberService.logout(userDetails.getMember(), httpServletRequest, httpServletResponse));
    }

    @Operation(summary = "전체 유저 조회", description = "DB에 등록된 유저 전체 조회")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/find")
    public ResponseEntity<ResponseDto> find() {

        return ResponseEntity.ok(memberService.find());
    }

    @Operation(summary = "유저 정보 수정", description = "유저 정보 수정")
    @ApiResponse(responseCode = "200", description = "SUCCESSED")
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> update(@Valid @RequestBody MemberUpdateRequest request) {
        Log.objectInfo(request);
        return ResponseEntity.ok(memberService.update(request));
    }

    @Operation(summary = "유저 정보 삭제", description = "delete 플래그 true || false 로 변경")
    @ApiResponse(responseCode = "200", description = "SUCCESSED")
    @PutMapping("/delete")
    public ResponseEntity<ResponseDto> delete(@RequestBody MemberAuthRequest request) {

        return ResponseEntity.ok(memberService.delete(request));
    }

}
