package com.goat.server.auth.application;

import com.goat.server.global.util.jwt.JwtUserDetails;
import com.goat.server.global.exception.CustomFeignException;
import com.goat.server.global.util.jwt.JwtTokenProvider;
import com.goat.server.mypage.domain.User;
import com.goat.server.mypage.repository.UserRepository;
import com.goat.server.mypage.application.UserService;
import com.goat.server.auth.dto.response.SignUpSuccessResponse;
import com.goat.server.auth.presentation.KakaoApiClient;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.goat.server.auth.dto.response.KakaoUserResponse;

import static com.goat.server.auth.exception.errorcode.AuthErrorCode.FEIGN_FAILED;


@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoSocialService {

    private final UserRepository userRepository;
    private final KakaoApiClient kakaoApiClient;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Transactional
    public SignUpSuccessResponse socialLogin(final String kakaoAccessToken) {

        KakaoUserResponse userResponse = getUserInformationFromKakao(kakaoAccessToken);

        User user = findOrCreateUser(userResponse);

        JwtUserDetails jwtUserDetails = new JwtUserDetails(user.getUserId(), user.getRole());

        return SignUpSuccessResponse.from(jwtTokenProvider.generateToken(jwtUserDetails));

    }

    private KakaoUserResponse getUserInformationFromKakao(String kakaoAccessToken) {
        try {
            return kakaoApiClient.getUserInformation("Bearer " + kakaoAccessToken);
        } catch (FeignException e) {
            throw new CustomFeignException(FEIGN_FAILED, "Failed to get user information from Kakao: " + e.contentUTF8());
        }
    }

    private User findOrCreateUser(KakaoUserResponse userResponse) {
        return userRepository.findBySocialId(userResponse.id().toString())
                .orElseGet(() -> userService.createUser(userResponse));
    }
}
