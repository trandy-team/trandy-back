package org.trandy.trandy_server.config;

import org.trandy.trandy_server.common.jwt.JwtAuthFilter;
import org.trandy.trandy_server.common.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        //스웨거, 아이콘 리소스 허용
        return web -> web.ignoring()
                .requestMatchers(
                        "/favicon.ico",
                        "/swagger-ui/**",
                        "/swagger-resource/**",
                        "/error",
                        "/v3/api-docs/**"
                )
                // static 경로 안 파일 웹 접근 허용
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .httpBasic((basic) -> basic.disable())
                .csrf((csrf) -> csrf.disable())
                //jwt 사용 세션 stateless
                .sessionManagement((sm) -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((auth) -> auth
                        // 해당 하는 Url 에 대해서는 모든 사람 접속 허용
                        .requestMatchers("/","/members/sign-in","/swagger-ui/**","/members/sign-up","/healthcheck").permitAll()
                        .requestMatchers("/members/authChange").permitAll()
                        .requestMatchers("/**").permitAll()
//                        .requestMatchers("/members/authCheck").hasRole(Role.ADMIN.name())
                        //.requestMatchers("/admin/**")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
