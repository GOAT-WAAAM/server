package com.goat.server.auth.application;

import com.goat.server.auth.dto.response.ReIssueSuccessResponse;
import com.goat.server.auth.dto.response.SignUpSuccessResponse;
import com.goat.server.global.domain.JwtUserDetails;
import com.goat.server.global.util.JwtTokenProvider;
import com.goat.server.mypage.domain.User;
import com.goat.server.mypage.domain.type.Role;
import com.goat.server.mypage.exception.UserNotFoundException;
import com.goat.server.mypage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.goat.server.mypage.exception.errorcode.MypageErrorCode.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public ReIssueSuccessResponse reIssueToken(String refreshToken) {

        Long userId = jwtTokenProvider.getJwtUserDetails(refreshToken).userId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        return ReIssueSuccessResponse
                .from(jwtTokenProvider.generateToken(JwtUserDetails
                        .of(userId, user.getRole())
                        )
                );
    }

    public SignUpSuccessResponse getTestToken() {

        return SignUpSuccessResponse
                .from(jwtTokenProvider.generateToken(JwtUserDetails
                        .of(1L, Role.USER)
                        )
                );
    }
}
