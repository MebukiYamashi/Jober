package com.fastcampus.jober.global.security.auth.jwt;

import com.fastcampus.jober.global.error.exception.TokenException;
import com.fastcampus.jober.global.utils.FilterResponseUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.fastcampus.jober.global.constant.ErrorCode.INVALID_AUTHENTICATION;

@Component
@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (TokenException e) {
            log.warn("인증되지 않은 사용자가 자원에 접근하려 합니다 : " + e.getMessage());
            FilterResponseUtils.setErrorResponse(INVALID_AUTHENTICATION.getHttpStatus(), response, e);
        }
    }
}
