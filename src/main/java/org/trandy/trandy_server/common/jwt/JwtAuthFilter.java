package org.trandy.trandy_server.common.jwt;

import org.trandy.trandy_server.common.Constants;
import org.trandy.trandy_server.common.jwt.enums.TokenState;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        String accessToken = null;
        String refreshToken = null;

        if(cookies != null){
            // 1. Bearer 제거한 토큰 값 가져오기
            for (Cookie cookie : cookies) {
                if (cookie == null) {
                    continue;
                }
                if (cookie.getName().equals("Authorization")) {
                    accessToken = jwtTokenProvider.resolveToken(cookie);
                }
                if (cookie.getName().equals("Refresh")) {
                    refreshToken = jwtTokenProvider.resolveToken(cookie);
                }
            }
        }

        // 토큰이 유효하면 인증객체 생성 후 SecurityContext -> SecurityContextHolder 에 저장
        if(accessToken != null){
            if(jwtTokenProvider.validateToken(accessToken) == TokenState.VALID){
                setAuthentication(jwtTokenProvider.getUserInfoFromToken(accessToken).getSubject());
            } else if (jwtTokenProvider.validateToken(accessToken) == TokenState.EXPIRED) {
                // Access Token 만료 시,
                // Access Token Cookie 삭제
                ResponseCookie responseCookie = ResponseCookie.from(Constants.AUTHORIZATION_HEADER, null).
                        path("/").
                        httpOnly(true).
                        sameSite("None").
                        secure(true).
                        maxAge(1).
                        build();
                response.addHeader("Set-Cookie", responseCookie.toString());

                throw new RuntimeException("만료된 JWT token 입니다.");
            }
        }
        else if (refreshToken != null){
            if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
                setAuthentication(jwtTokenProvider.getUserInfoFromToken(refreshToken).getSubject());
            }
        }

        filterChain.doFilter(request, response);
    }

    public void setAuthentication(String email){
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtTokenProvider.createAuthentication(email);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }
}
