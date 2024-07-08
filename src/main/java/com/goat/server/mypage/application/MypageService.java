package com.goat.server.mypage.application;

import com.goat.server.mypage.domain.User;
import com.goat.server.mypage.dto.request.GoalRequest;
import com.goat.server.mypage.dto.request.MypageDetailsRequest;
import com.goat.server.mypage.dto.response.MypageDetailsResponse;
import com.goat.server.mypage.dto.response.MypageHomeResponse;
import com.goat.server.mypage.exception.UserNotFoundException;
import com.goat.server.mypage.exception.errorcode.MypageErrorCode;
import com.goat.server.mypage.repository.UserRepository;
import com.goat.server.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MypageService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    /**
     * 마이페이지에서 닉네임, 복습횟수, 한줄목표, 프로필 이미지 조회하기
     */
    public MypageHomeResponse getMypageHome(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(MypageErrorCode.USER_NOT_FOUND));

        Long totalReviewCnt = reviewRepository.sumReviewCntByUser(user.getUserId());

        return MypageHomeResponse.of(user, totalReviewCnt);
    }

    /**
     * 마이페이지에서 세부 정보 조회하기 (프로필 이미지, 닉네임, 전공들, 학년 )
     */
    public MypageDetailsResponse getMypageDetails(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(MypageErrorCode.USER_NOT_FOUND));

        return MypageDetailsResponse.from(user);
    }

    /**
     * 마이페이지에서 세부 정보 수정하기 (닉네임)
     */
    @Transactional
    public void updateMypageDetails(Long userId, MypageDetailsRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(MypageErrorCode.USER_NOT_FOUND));

        user.updateMypageDetails(request);
    }

    /**
     * 한줄 목표 업데이트
     */
    @Transactional
    public void updateGoal(Long userId, GoalRequest goalRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(MypageErrorCode.USER_NOT_FOUND));
        user.updateGoal(goalRequest.goal());
    }
}