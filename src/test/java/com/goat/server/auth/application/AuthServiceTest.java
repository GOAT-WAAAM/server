package com.goat.server.auth.application;

import com.goat.server.auth.dto.response.ReIssueSuccessResponse;
import com.goat.server.global.domain.JwtUserDetails;
import com.goat.server.global.domain.type.Tokens;
import com.goat.server.global.util.JwtTokenProvider;
import static com.goat.server.mypage.fixture.UserFixture.USER_USER;
import com.goat.server.mypage.domain.type.Role;
import com.goat.server.mypage.repository.JwtUserDetailProjection;
import com.goat.server.mypage.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("토큰 재발급 테스트")
    void reIssueToken() {
        // given
        given(userRepository.findJwtUserDetailsById(1L)).willReturn(new JwtUserDetailProjection() {
            @Override
            public Long getUserId() {
                return 1L;
            }

            @Override
            public String getRole() {
                return Role.USER.toString();
            }
        });
        given(jwtTokenProvider.getJwtUserDetails("refreshToken")).willReturn(new JwtUserDetails(1L, Role.USER));
        given(jwtTokenProvider.generateToken(new JwtUserDetails(1L, Role.USER)))
                .willReturn(new Tokens("reIssuedAccessToken", "reIssuedRefreshToken"));

        // when
        ReIssueSuccessResponse reIssueSuccessResponse = authService.reIssueToken("refreshToken");

        // then
        assertEquals("reIssuedAccessToken", reIssueSuccessResponse.accessToken());
    }


}