package com.goat.server.directory.application;

import com.goat.server.directory.domain.Directory;
import com.goat.server.directory.dto.response.DirectoryResponse;
import com.goat.server.directory.dto.response.DirectoryResponseList;
import com.goat.server.directory.exception.DirectoryNotFoundException;
import com.goat.server.directory.exception.errorcode.DirectoryErrorCode;
import com.goat.server.directory.repository.DirectoryRepository;
import com.goat.server.mypage.domain.User;
import com.goat.server.mypage.exception.UserNotFoundException;
import com.goat.server.mypage.exception.errorcode.MypageErrorCode;
import com.goat.server.mypage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class DirectoryService {

    private final UserRepository userRepository;
    private final DirectoryRepository directoryRepository;

    /**
     * 유저의 과목과 폴더 목록을 조회
     */
    public DirectoryResponseList getDirectoryList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(MypageErrorCode.USER_NOT_FOUND));

        return DirectoryResponseList.from(directoryRepository.findAllByUserAndParentDirectoryIsNull(user).stream()
                .map(DirectoryResponse::from)
                .toList());
    }

    @Transactional
    public void deleteDirectory(Long userId, Long directoryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(MypageErrorCode.USER_NOT_FOUND));
        Directory directory = directoryRepository.findById(directoryId)
                .orElseThrow(() -> new DirectoryNotFoundException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));

        directoryRepository.findTrashDirectoryByUser(user)
                .ifPresentOrElse(
                        directory::updateParentDirectory,
                        () -> {
                            log.error("trash directory not found");
                            throw new DirectoryNotFoundException(DirectoryErrorCode.DIRECTORY_NOT_FOUND);
                        });
    }
}
