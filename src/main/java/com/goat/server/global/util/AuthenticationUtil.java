package com.goat.server.global.util;

import com.goat.server.auth.exception.TokenNotFoundException;
import com.goat.server.global.util.filter.UserAuthentication;
import com.goat.server.global.util.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static com.goat.server.auth.exception.errorcode.AuthErrorCode.INVALID_TOKEN;
import static com.goat.server.global.domain.type.JwtValidationType.VALID_JWT;

@Component
@RequiredArgsConstructor
public class AuthenticationUtil {

    private final JwtTokenProvider jwtTokenProvider;

    public void setAuthenticationFromRequest(HttpServletRequest request) {

        final String token = getJwtFromRequest(request);

        if (isTokenValid(token)) {
            Authentication authentication = makeAuthentication(request, token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private UserAuthentication makeAuthentication(HttpServletRequest request, String token) {

        UserAuthentication authentication = UserAuthentication.from(jwtTokenProvider.getJwtUserDetails(token));

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        return authentication;
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        } else {
            throw new TokenNotFoundException(INVALID_TOKEN);
        }

    }

    private boolean isTokenValid(String token) {
        return token != null && jwtTokenProvider.validateToken(token) == VALID_JWT;
    }
}
