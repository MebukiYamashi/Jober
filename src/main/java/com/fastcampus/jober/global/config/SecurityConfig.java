package com.fastcampus.jober.global.config;

import com.fastcampus.jober.global.auth.jwt.JwtAuthenticationFilter;
import com.fastcampus.jober.global.auth.jwt.JwtExceptionFilter;
import com.fastcampus.jober.global.error.exception.TokenException;
import com.fastcampus.jober.global.utils.FilterResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.fastcampus.jober.global.constant.ErrorCode.INVALID_AUTHENTICATION;
import static com.fastcampus.jober.global.constant.ErrorCode.INVALID_USER;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtExceptionFilter jwtExceptionFilter;

//    @Bean
//    BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 비밀번호 인코딩을 비활성화
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }

    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {

        @Override
        public void configure(HttpSecurity builder) throws Exception {

            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            builder.addFilter(new JwtAuthenticationFilter(authenticationManager));
            super.configure(builder);
        }
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // 0. 필터 차원에서 토큰 예외처리 하기 위함
        http.addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class);

        // 1. CSRF 해제
        http.csrf(AbstractHttpConfigurer::disable);

        // 2. iframe 거부
        http.headers(c1 -> c1.frameOptions(c2 -> c2.sameOrigin()));

        // 3. cors 재설정
        http.cors(c -> c.configurationSource(configurationSource()));

        // 4. jSessionId 사용 거부
        http.sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 5. form 로긴 해제 (UsernamePasswordAuthenticationFilter 비활성화)
        http.formLogin(AbstractHttpConfigurer::disable);

        // 6. 로그인 인증창이 뜨지 않게 비활성화
        http.httpBasic(AbstractHttpConfigurer::disable);

        // 6-1. 로그아웃 url 지정 및 로그아웃 후 리다이렉트 url 지정
//        http.logout(c -> c.logoutUrl("/logout").addLogoutHandler(logoutHandler)
//                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
//        );

        // 7. 커스텀 필터 적용 (시큐리티 필터 교환)
        http.apply(new CustomSecurityFilterManager());

        // 8. 인증 실패 처리
        http.exceptionHandling(
                c -> c.authenticationEntryPoint((request, response, authException) -> {
                    log.warn("인증되지 않은 사용자가 자원에 접근하려 합니다 : " + authException.getMessage());
                    FilterResponseUtils.setErrorResponse(
                            INVALID_AUTHENTICATION.getHttpStatus(),
                            response,
                            new TokenException(INVALID_AUTHENTICATION)
                    );
                }));

        // 9. 권한 실패 처리
        http.exceptionHandling(
                c -> c.accessDeniedHandler((request, response, accessDeniedException) -> {
                    log.warn("권한이 없는 사용자가 자원에 접근하려 합니다 : " + accessDeniedException.getMessage());
                    FilterResponseUtils.setErrorResponse(
                            INVALID_USER.getHttpStatus(),
                            response,
                            new TokenException(INVALID_USER)
                    );
                }));

        // 11. 인증, 권한 필터 설정
        http.authorizeRequests(
                authorize -> {
                    authorize
                            .requestMatchers(
                                    new AntPathRequestMatcher("/my-spaces"),
                                    new AntPathRequestMatcher("/new-spaces"),
                                    new AntPathRequestMatcher("/check-token"),
                                    new AntPathRequestMatcher("/check-email/**")
                            )
                            .access("isAuthenticated()")
                            .requestMatchers(new AntPathRequestMatcher("/spaces/history/**"))
                            .access("isAuthenticated() and hasAnyAuthority('EDITOR', 'OWNER')")
                            .requestMatchers(new AntPathRequestMatcher("/spaces/member/**"))
                            .access("isAuthenticated() and hasAnyAuthority('OWNER')")
                            .anyRequest().permitAll();
                });
        return http.build();
    }

    public CorsConfigurationSource configurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*"); // GET, POST, PUT, DELETE (Javascript 요청 허용)
        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용 (프론트 앤드 IP만 허용 react)
        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용
        configuration.addExposedHeader("Authorization"); // 옛날에는 디폴트 였다. 지금은 아닙니다.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
