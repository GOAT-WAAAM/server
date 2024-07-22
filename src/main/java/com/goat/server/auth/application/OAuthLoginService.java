package com.goat.server.auth.application;

import com.goat.server.auth.dto.request.KakaoLoginParams;
import com.goat.server.auth.dto.request.NaverLoginParams;
import com.goat.server.auth.dto.request.OAuthLoginParams;
import com.goat.server.auth.dto.response.OAuthInfoResponse;
import com.goat.server.global.util.jwt.JwtUserDetails;
import com.goat.server.global.util.jwt.JwtTokenProvider;
import com.goat.server.mypage.domain.User;
import com.goat.server.mypage.application.UserService;
import com.goat.server.auth.dto.response.SignUpSuccessResponse;
import com.goat.server.review.application.ReviewService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthLoginService {

    private final UserService userService;
    private final ReviewService reviewService;
    private final RequestOAuthInfoService requestOAuthInfoService;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public SignUpSuccessResponse socialLogin(String provider, String accessToken) {

        log.info("[OAuthLoginService.socialLogin]");

        OAuthLoginParams oAuthLoginParams = generateOAuthLoginParams(provider, accessToken);

        OAuthInfoResponse infoResponse = requestOAuthInfoService.request(oAuthLoginParams);

        User user = userService.findOrCreateUser(infoResponse);

        Long totalReviewCount = reviewService.calculateReviewCount(user.getUserId());

        JwtUserDetails jwtUserDetails = new JwtUserDetails(user.getUserId(), user.getRole());

        return SignUpSuccessResponse.of(jwtTokenProvider.generateToken(jwtUserDetails), user, totalReviewCount);

    }

    private OAuthLoginParams generateOAuthLoginParams(String provider, String accessToken) {
        if (provider.equals("KAKAO")) {
            return new KakaoLoginParams(accessToken);
        }

        if (provider.equals("NAVER")) {
            return new NaverLoginParams(accessToken);
        }

        throw new IllegalArgumentException("Invalid provider: " + provider);
    }

}
