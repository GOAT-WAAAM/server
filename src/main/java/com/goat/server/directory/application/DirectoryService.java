package com.goat.server.directory.application;

import com.goat.server.directory.domain.Directory;
import com.goat.server.directory.dto.request.DirectoryInitRequest;
import com.goat.server.directory.dto.response.DirectoryResponse;
import com.goat.server.directory.dto.response.DirectoryTotalShowResponse;
import com.goat.server.directory.exception.DirectoryNotFoundException;
import com.goat.server.directory.exception.errorcode.DirectoryErrorCode;
import com.goat.server.directory.repository.DirectoryRepository;
import com.goat.server.mypage.application.UserService;
import com.goat.server.mypage.domain.User;
import com.goat.server.review.application.ReviewService;
import com.goat.server.review.dto.response.ReviewSimpleResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class DirectoryService {

    private final DirectoryRepository directoryRepository;
    private final ReviewService reviewService;
    private final UserService userService;

    /**
     * 유저의 과목과 폴더 목록을 조회
     */
    public DirectoryTotalShowResponse getDirectorySubList(Long userId, Long directoryId) {

        List<DirectoryResponse> directoryResponseList = getDirectoryResponseList(userId, directoryId);
        List<ReviewSimpleResponse> reviewSimpleResponseList = reviewService.getReviewSimpleResponseList(directoryId);

        log.info("reviewSimpleResponseList: {}", reviewSimpleResponseList.size());

        return DirectoryTotalShowResponse.of(directoryResponseList, reviewSimpleResponseList);
    }

    @Transactional
    public void deleteDirectoryTemporal(Long userId, Long directoryId) {

        Directory directory = directoryRepository.findById(directoryId)
                .orElseThrow(() -> new DirectoryNotFoundException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));

        touchParentDirectories(directory);

        directoryRepository.findTrashDirectoryByUser(userId)
                .ifPresentOrElse(
                        directory::updateParentDirectory,
                        () -> {
                            log.error("trash directory not found");
                            throw new DirectoryNotFoundException(DirectoryErrorCode.DIRECTORY_NOT_FOUND);
                        });
    }

    @Transactional
    public void initDirectory(Long userId, DirectoryInitRequest directoryInitRequest) {

        User user = userService.findUser(userId);
        Directory parentDirectory = getDirectory(directoryInitRequest);

        touchParentDirectories(parentDirectory);

        directoryRepository.save(directoryInitRequest.toEntity(user, parentDirectory));
    }

    private Directory getDirectory(DirectoryInitRequest directoryInitRequest) {
        Directory parentDirectory;

        if (directoryInitRequest.parentDirectoryId() == 0) {
            parentDirectory = null;
        } else {
            parentDirectory = directoryRepository.findById(directoryInitRequest.parentDirectoryId())
                    .orElseThrow(() -> new DirectoryNotFoundException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));
        }

        return parentDirectory;
    }

    private List<DirectoryResponse> getDirectoryResponseList(Long userId, Long parentDirectoryId) {
        List<Directory> directoryList =
                parentDirectoryId == 0 ? directoryRepository.findAllByUserIdAndParentDirectoryIsNull(userId)
                        : directoryRepository.findByParentDirectoryId(parentDirectoryId);
        //해당 메서드 없는 폴더 보려고 하면 exception 처리하기
        log.info("directoryList: {}", directoryList.size());

        return directoryList.stream()
                .map(DirectoryResponse::from)
                .toList();
    }

    private void touchParentDirectories(Directory directory) {
        while (directory != null) {
            directory.updateModifiedDate();
            directory = directory.getParentDirectory();
        }
    }
}
