package com.goat.server.auth.application;

import com.goat.server.auth.dto.request.OnBoardingRequest;
import com.goat.server.auth.dto.response.ReIssueSuccessResponse;
import com.goat.server.directory.repository.DirectoryRepository;
import com.goat.server.global.application.S3Uploader;
import com.goat.server.global.util.jwt.JwtUserDetails;
import com.goat.server.global.util.jwt.JwtTokenProvider;
import com.goat.server.mypage.domain.User;
import com.goat.server.mypage.exception.UserNotFoundException;
import com.goat.server.mypage.repository.UserRepository;
import com.goat.server.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
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
    private final S3Uploader s3Uploader;

    public ReIssueSuccessResponse reIssueToken(String refreshToken) {

        Long userId = jwtTokenProvider.getJwtUserDetails(refreshToken).userId();

        return ReIssueSuccessResponse
                .from(jwtTokenProvider.generateToken(getJwtUserDetails(userId)));
    }

    public String getTestToken(Long userId) {
        return jwtTokenProvider.generateToken(getJwtUserDetails(userId)).accessToken();
    }

    public JwtUserDetails getJwtUserDetails(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        return JwtUserDetails.from(user);
    }

    @Transactional
    public void saveOnBoardingInfo(Long userId, OnBoardingRequest onBoardingRequest) {
        log.info("[AuthService.saveOnBoardingInfo]");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        user.updateOnBoardingInfo(onBoardingRequest.nickname(), onBoardingRequest.goal());

        if(!onBoardingRequest.fcmToken().isBlank()) {
            user.updateFcmToken(onBoardingRequest.fcmToken());
        }

        userRepository.save(user);
    }

    public void withdraw(Long userId) {

        log.info("[AuthService.withdraw]");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        s3Uploader.deleteImage(user.getImageInfo());

        userRepository.deleteById(userId);
    }
}
