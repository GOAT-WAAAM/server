package com.goat.server.auth.application;

import com.goat.server.auth.dto.request.KakaoLoginParams;
import com.goat.server.auth.dto.request.OAuthLoginParams;
import com.goat.server.auth.dto.response.OAuthInfoResponse;
import com.goat.server.global.util.jwt.JwtUserDetails;
import com.goat.server.global.util.jwt.JwtTokenProvider;
import com.goat.server.mypage.domain.User;
import com.goat.server.mypage.repository.UserRepository;
import com.goat.server.mypage.application.UserService;
import com.goat.server.auth.dto.response.SignUpSuccessResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthLoginService {

    private final UserRepository userRepository;
    private final RequestOAuthInfoService requestOAuthInfoService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Transactional
    public SignUpSuccessResponse socialLogin(String provider, String accessToken) {

        log.info("[OAuthLoginService.socialLogin]");

        OAuthLoginParams oAuthLoginParams = generateOAuthLoginParams(provider, accessToken);

        OAuthInfoResponse infoResponse = requestOAuthInfoService.request(oAuthLoginParams);

        User user = findOrCreateUser(infoResponse);

        JwtUserDetails jwtUserDetails = new JwtUserDetails(user.getUserId(), user.getRole());

        return SignUpSuccessResponse.from(jwtTokenProvider.generateToken(jwtUserDetails));

    }

    private OAuthLoginParams generateOAuthLoginParams(String provider, String accessToken) {
        if (provider.equals("KAKAO")) {
            return new KakaoLoginParams(accessToken);
        }

        if (provider.equals("NAVER")) {
//            return new NaverLoginParams(accessToken);
        }

        throw new IllegalArgumentException("Invalid provider: " + provider);
    }

    private User findOrCreateUser(OAuthInfoResponse infoResponse) {
        return userRepository.findBySocialId(infoResponse.getId().toString())
                .orElseGet(() -> userService.createUser(infoResponse));
    }
}
