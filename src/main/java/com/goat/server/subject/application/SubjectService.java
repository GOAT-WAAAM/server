package com.goat.server.subject.application;

import com.goat.server.mypage.domain.User;
import com.goat.server.mypage.exception.UserNotFoundException;
import com.goat.server.mypage.exception.errorcode.MypageErrorCode;
import com.goat.server.mypage.repository.UserRepository;
import com.goat.server.subject.dto.response.SubjectResponse;
import com.goat.server.subject.dto.response.SubjectResponseList;
import com.goat.server.subject.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class SubjectService {

    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    /**
     * 유저의 과목과 폴더 목록을 조회
     */
    public SubjectResponseList getSubjectsAndDirectories(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(MypageErrorCode.USER_NOT_FOUND));

        return SubjectResponseList.from(subjectRepository.findSubjectsAndDirectories(user).stream()
                .map(SubjectResponse::from)
                .toList());
    }
}
