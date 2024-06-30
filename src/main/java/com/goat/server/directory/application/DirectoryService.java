package com.goat.server.directory.application;

import com.goat.server.directory.application.type.SortType;
import com.goat.server.directory.domain.Directory;
import com.goat.server.directory.dto.request.DirectoryInitRequest;
import com.goat.server.directory.dto.request.DirectoryMoveRequest;
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
import org.springframework.util.ObjectUtils;

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
    public DirectoryTotalShowResponse getDirectorySubList(
            Long userId, Long directoryId, List<SortType> sort, String search) {

        validateDirectory(directoryId);

        List<DirectoryResponse> directoryResponseList =
                getDirectoryResponseList(userId, directoryId, sort, search);
        List<ReviewSimpleResponse> reviewSimpleResponseList =
                reviewService.getReviewSimpleResponseList(userId, directoryId, sort, search);

        return DirectoryTotalShowResponse.of(directoryId, directoryResponseList, reviewSimpleResponseList);
    }

    /**
     * 폴더 임시 삭제
     */
    @Transactional
    public void deleteDirectoryTemporal(Long userId, Long directoryId) {

        Directory directory = directoryRepository.findById(directoryId)
                .orElseThrow(() -> new DirectoryNotFoundException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));

        directory.validateUser(userId);

        directory.touchParentDirectories();

        directoryRepository.findTrashDirectoryByUser(userId)
                .ifPresentOrElse(
                        directory::updateParentDirectory,
                        () -> {
                            log.error("trash directory not found");
                            throw new DirectoryNotFoundException(DirectoryErrorCode.DIRECTORY_NOT_FOUND);
                        });
    }

    /**
     * 폴더 영구 삭제
     */
    @Transactional
    public void deleteDirectoryPermanent(Long userId, Long directoryId) {

        Directory directory = directoryRepository.findById(directoryId)
                .orElseThrow(() -> new DirectoryNotFoundException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));

        directory.validateUser(userId);

        directory.touchParentDirectories();
        directoryRepository.delete(directory);
    }

    /**
     * 폴더 생성
     */
    @Transactional
    public void initDirectory(Long userId, DirectoryInitRequest directoryInitRequest) {

        User user = userService.findUser(userId);
        Directory parentDirectory = getParentDirectory(directoryInitRequest);

        if (parentDirectory != null) {
            parentDirectory.touchParentDirectories();
        }

        directoryRepository.save(directoryInitRequest.toEntity(user, parentDirectory));
    }

    /**
     * 폴더 이동
     */
    @Transactional
    public void moveDirectory(DirectoryMoveRequest request) {
        Directory moveDirectory = directoryRepository.findById(request.moveDirectoryId())
                .orElseThrow(() -> new DirectoryNotFoundException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));
        Directory targetDirectory = directoryRepository.findById(request.targetDirectoryId())
                .orElseThrow(() -> new DirectoryNotFoundException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));

        moveDirectory.touchParentDirectories(); //이전 부모 폴더들 touch

        moveDirectory.updateParentDirectory(targetDirectory);
        moveDirectory.touchParentDirectories(); //현재 부모 폴더들 touch
    }

    private Directory getParentDirectory(DirectoryInitRequest directoryInitRequest) {
        Directory parentDirectory;

        if (directoryInitRequest.parentDirectoryId() == 0) {
            parentDirectory = null;
        } else {
            parentDirectory = directoryRepository.findById(directoryInitRequest.parentDirectoryId())
                    .orElseThrow(() -> new DirectoryNotFoundException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));
        }

        return parentDirectory;
    }

    private void validateDirectory(Long directoryId) {
        if (directoryId != 0 && !directoryRepository.existsById(directoryId)) {
            throw new DirectoryNotFoundException(DirectoryErrorCode.DIRECTORY_NOT_FOUND);
        }
    }

    private List<DirectoryResponse> getDirectoryResponseList(
            Long userId, Long parentDirectoryId, List<SortType> sort, String search) {

        if (!ObjectUtils.isEmpty(search)) {
            return directoryRepository.findAllBySearch(userId, search, sort).stream()
                    .map(DirectoryResponse::from)
                    .toList();
        }

        List<Directory> directoryList =
                parentDirectoryId == 0 ?
                        directoryRepository.findAllByUserIdAndParentDirectoryIsNull(userId, sort)
                        : directoryRepository.findAllByParentDirectoryId(parentDirectoryId, sort);

        return directoryList.stream()
                .map(DirectoryResponse::from)
                .toList();
    }
}
