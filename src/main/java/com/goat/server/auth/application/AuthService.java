package com.goat.server.auth.application;

import com.goat.server.auth.dto.response.ReIssueSuccessResponse;
import com.goat.server.auth.dto.response.SignUpSuccessResponse;
import com.goat.server.global.domain.JwtUserDetails;
import com.goat.server.global.util.JwtTokenProvider;
import com.goat.server.mypage.domain.type.Role;
import com.goat.server.mypage.exception.UserNotFoundException;
import com.goat.server.mypage.repository.JwtUserDetailProjection;
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

        return ReIssueSuccessResponse
                .from(jwtTokenProvider.generateToken(getJwtUserDetails(userId)));
    }

    public SignUpSuccessResponse getTestToken(Long userId) {

        return SignUpSuccessResponse
                .from(jwtTokenProvider.generateToken(getJwtUserDetails(userId)));
    }

    public JwtUserDetails getJwtUserDetails(Long userId) {
        JwtUserDetailProjection jwtUserDetailProjection = userRepository.findJwtUserDetailsById(userId);

        if (jwtUserDetailProjection == null) {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }

        return JwtUserDetails.of(jwtUserDetailProjection.getUserId(), Role.valueOf(jwtUserDetailProjection.getRole()));
    }
}
