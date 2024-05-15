package com.goat.server.global.util.filter;

import com.goat.server.global.util.ErrorResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.goat.server.auth.exception.errorcode.AuthErrorCode.INVALID_TOKEN;

@Slf4j
@Component
public class CustomJwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.warn("[JwtAuthenticationEntryPoint.commence] error message: {}", authException.getMessage());

        ErrorResponseUtil.setResponse(response, INVALID_TOKEN);
    }
}