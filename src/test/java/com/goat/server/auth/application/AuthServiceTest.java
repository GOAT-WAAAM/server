package com.goat.server.auth.application;

import com.goat.server.auth.dto.response.ReIssueSuccessResponse;
import com.goat.server.global.domain.type.Tokens;
import com.goat.server.global.util.JwtTokenProvider;
import com.goat.server.global.util.filter.UserAuthentication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("토큰 재발급 테스트")
    void reIssueToken() {
        // given
        given(jwtTokenProvider.getUserIdFromJwt("refreshToken")).willReturn(1L);
        given(jwtTokenProvider.generateToken(new UserAuthentication(1L,null, null)))
                .willReturn(new Tokens("reIssuedAccessToken", "reIssuedRefreshToken"));

        // when
        ReIssueSuccessResponse reIssueSuccessResponse = authService.reIssueToken("refreshToken");

        // then
        assertEquals("reIssuedAccessToken", reIssueSuccessResponse.accessToken());
    }


}