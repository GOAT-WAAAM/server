package com.goat.server.auth.application;

import com.goat.server.auth.dto.OnBoardingRequest;
import com.goat.server.auth.dto.response.ReIssueSuccessResponse;
import com.goat.server.directory.domain.Directory;
import com.goat.server.directory.fixture.DirectoryFixture;
import com.goat.server.directory.repository.DirectoryRepository;
import com.goat.server.global.application.S3Uploader;
import com.goat.server.global.util.jwt.JwtUserDetails;
import com.goat.server.global.util.jwt.Tokens;
import com.goat.server.global.util.jwt.JwtTokenProvider;
import com.goat.server.mypage.domain.type.Role;
import com.goat.server.mypage.repository.UserRepository;
import com.goat.server.review.domain.Review;
import com.goat.server.review.fixture.ReviewFixture;
import com.goat.server.review.repository.ReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.goat.server.mypage.fixture.UserFixture.USER_GUEST;
import static com.goat.server.mypage.fixture.UserFixture.USER_USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserRepository userRepository;

    @Mock
    private S3Uploader s3Uploader;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private DirectoryRepository directoryRepository;

    @Test
    @DisplayName("토큰 재발급 테스트")
    void reIssueToken() {
        // given
        given(userRepository.findById(1L)).willReturn(Optional.of(USER_USER));
        given(jwtTokenProvider.getJwtUserDetails("refreshToken")).willReturn(new JwtUserDetails(1L, Role.USER));
        given(jwtTokenProvider.generateToken(JwtUserDetails.from(USER_USER)))
                .willReturn(new Tokens("reIssuedAccessToken", "reIssuedRefreshToken"));

        // when
        ReIssueSuccessResponse reIssueSuccessResponse = authService.reIssueToken("refreshToken");

        // then
        assertEquals("reIssuedAccessToken", reIssueSuccessResponse.accessToken());
    }

    @Test
    @DisplayName("온보딩 회원정보 입력 테스트")
    void saveOnBoardingInfo() {
        // given
        given(userRepository.findById(1L)).willReturn(Optional.of(USER_GUEST));

        // when
        authService.saveOnBoardingInfo(1L, new OnBoardingRequest("modifiedNickname", "modifiedGoal", "fcmToken"));

        // then
        assertEquals("modifiedNickname", USER_GUEST.getNickname());
        assertEquals("modifiedGoal", USER_GUEST.getGoal());
        assertEquals("fcmToken", USER_GUEST.getFcmToken());
        assertEquals(Role.USER, USER_GUEST.getRole());
    }

    @Test
    @DisplayName("회원탈퇴 테스트")
    void withdraw() {
        // given
        given(userRepository.findById(USER_USER.getUserId())).willReturn(Optional.of(USER_USER));

        // when
        authService.withdraw(USER_USER.getUserId());

        // then
        verify(userRepository).deleteById(USER_USER.getUserId());
    }
}