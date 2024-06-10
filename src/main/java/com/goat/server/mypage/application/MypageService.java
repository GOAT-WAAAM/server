package com.goat.server.mypage.application;

import com.goat.server.mypage.domain.Major;
import com.goat.server.mypage.domain.User;
import com.goat.server.mypage.dto.request.GoalRequest;
import com.goat.server.mypage.dto.response.UserMajorResponse;
import com.goat.server.mypage.exception.UserNotFoundException;
import com.goat.server.mypage.exception.errorcode.MypageErrorCode;
import com.goat.server.mypage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MypageService {

    private final UserRepository userRepository;

    /**
     * 마이페이지에서 닉네임, 학년, 전공들, 한줄목표 조회하기
     */
    public UserMajorResponse getUserWithMajors(Long userId) {
        User user = userRepository.findUserWithMajors(userId);
        if (user == null) {
            throw new UserNotFoundException(MypageErrorCode.USER_NOT_FOUND);
        }

        List<String> majorNames = user.getMajorList().stream()
                .map(Major::getMajorName)
                .collect(Collectors.toList());

        return new UserMajorResponse(user.getNickname(), user.getGrade(), user.getGoal(), majorNames);
    }

    /**
     * 한줄 목표 업데이트
     */
    @Transactional
    public void updateGoal(Long userId, GoalRequest goalRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(MypageErrorCode.USER_NOT_FOUND));

        user.updateGoal(goalRequest.goal());
        userRepository.save(user);
    }

}
