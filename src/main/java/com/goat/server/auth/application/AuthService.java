package com.goat.server.auth.application;

import com.goat.server.auth.dto.response.ReIssueSuccessResponse;
import com.goat.server.global.util.JwtTokenProvider;
import com.goat.server.global.util.filter.UserAuthentication;
import com.goat.server.mypage.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    public ReIssueSuccessResponse reIssueToken(String refreshToken) {

        Long userId = jwtTokenProvider.getUserFromJwt(refreshToken);

        UserAuthentication userAuthentication = new UserAuthentication(userId, null, null);

        return ReIssueSuccessResponse.From(jwtTokenProvider.generateToken(userAuthentication));
    }
}
