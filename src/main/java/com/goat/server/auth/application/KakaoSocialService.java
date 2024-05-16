package com.goat.server.auth.application;

import com.goat.server.global.exception.CustomFeignException;
import com.goat.server.global.util.JwtTokenProvider;
import com.goat.server.global.util.filter.UserAuthentication;
import com.goat.server.mypage.domain.User;
import com.goat.server.mypage.repository.UserRepository;
import com.goat.server.mypage.service.UserService;
import com.goat.server.auth.dto.response.SignUpSuccessResponse;
import com.goat.server.auth.presentation.KakaoApiClient;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import com.goat.server.auth.dto.response.KakaoUserResponse;

import java.util.Collections;

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

        UserAuthentication userAuthentication =
                new UserAuthentication(user.getUserId(),
                null,
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())));

        return SignUpSuccessResponse.From(jwtTokenProvider.generateToken(userAuthentication));

    }

    private KakaoUserResponse getUserInformationFromKakao(String kakaoAccessToken) {
        try {
            KakaoUserResponse userResponse = kakaoApiClient.getUserInformation("Bearer " + kakaoAccessToken);
            log.info("[KakaoSocialService.getUserInformationFromKakao] kakaoAccessToken: {}, userResponse: {}",kakaoAccessToken, userResponse);
            return userResponse;
        } catch (FeignException e) {
            throw new CustomFeignException(FEIGN_FAILED, "Failed to get user information from Kakao: " + e.contentUTF8());
        }
    }

    private User findOrCreateUser(KakaoUserResponse userResponse) {
        User user = userRepository.findBySocialId(userResponse.id().toString());
        return (user != null) ? user : userService.createUser(userResponse);
    }
}
