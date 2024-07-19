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
import jakarta.validation.Valid;
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
    public DirectoryTotalShowResponse getDirectorySubList(
            Long userId, Long directoryId, List<SortType> sort, String search) {

        validateDirectory(directoryId);

        List<DirectoryResponse> directoryResponseList =
                directoryRepository.findAllDirectoryResponseBySortAndSearch(userId, directoryId, sort, search);
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

        directory.validateUserDirectory(userId);

        directory.touchParentDirectories();

        directoryRepository.findTrashDirectoryByUser(userId)
                .ifPresent(directory::updateParentDirectory);
    }

    /**
     * 폴더 영구 삭제
     */
    @Transactional
    public void deleteDirectoryPermanent(Long userId, Long directoryId) {

        Directory directory = directoryRepository.findById(directoryId)
                .orElseThrow(() -> new DirectoryNotFoundException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));

        directory.validateUserDirectory(userId);

        directory.touchParentDirectories();

        directoryRepository.delete(directory);
    }

    /**
     * 폴더 생성
     */
    @Transactional
    public void initDirectory(Long userId, @Valid DirectoryInitRequest directoryInitRequest) {

        User user = userService.findUser(userId);
        Directory parentDirectory = getParentDirectory(directoryInitRequest);

        if (parentDirectory != null) {
            parentDirectory.touchParentDirectories();
        }

        directoryRepository.save(directoryInitRequest.toEntity(user, parentDirectory));
    }

    //parentDirectoryId가 0이면 directory 찾아오지 못해서 null - null 경우 루트 폴더로 생성
    private Directory getParentDirectory(DirectoryInitRequest directoryInitRequest) {
        return directoryRepository.findById(directoryInitRequest.parentDirectoryId())
                .orElse(null);
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

    private void validateDirectory(Long directoryId) {
        if (directoryId != 0 && !directoryRepository.existsById(directoryId)) {
            throw new DirectoryNotFoundException(DirectoryErrorCode.DIRECTORY_NOT_FOUND);
        }
    }
}
