package com.goat.server.review.application;

import com.goat.server.global.application.S3Uploader;
import com.goat.server.global.config.SchedulerConfiguration;
import com.goat.server.global.domain.ImageInfo;
import com.goat.server.mypage.application.UserService;
import com.goat.server.mypage.domain.User;
import com.goat.server.mypage.exception.UserNotFoundException;
import com.goat.server.mypage.exception.errorcode.MypageErrorCode;
import com.goat.server.mypage.repository.UserRepository;
import com.goat.server.review.domain.Review;
import com.goat.server.review.domain.ReviewDate;
import com.goat.server.review.dto.request.ReviewUpdateRequest;
import com.goat.server.review.dto.request.ReviewUploadRequest;
import com.goat.server.review.dto.response.*;
import com.goat.server.review.exception.ReviewNotFoundException;
import com.goat.server.review.exception.errorcode.ReviewErrorCode;
import com.goat.server.directory.application.type.SortType;
import com.goat.server.review.dto.response.ReviewSimpleResponse;
import com.goat.server.review.repository.ReviewRepository;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReviewService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final S3Uploader s3Uploader;
    private final UserService userService;

    private final SchedulerConfiguration schedulerConfiguration;

    private static final int PAGE_SIZE_HOME = 4;

    /**
     * 폴더에 속한 리뷰 목록 조회
     */
    public List<ReviewSimpleResponse> getReviewSimpleResponseList(
            Long userId, Long directoryId, List<SortType> sort, String search) {

        if (!ObjectUtils.isEmpty(search)) {
            return reviewRepository.findAllBySearch(userId, search, sort).stream()
                    .map(ReviewSimpleResponse::from)
                    .toList();
        }

        return reviewRepository.findAllByDirectoryId(directoryId, sort).stream()
                .map(ReviewSimpleResponse::from)
                .toList();
    }

    /**
     * 홈 화면에서 최근 등록 복습 이미지 조회
     */
    public ReviewHomeResponseList getRecentReviews(Long userId, int page){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(MypageErrorCode.USER_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, PAGE_SIZE_HOME, Sort.by("createdDate").descending());
        Page<Review> reviews = reviewRepository.findAllReviewImageByUserId(user.getUserId(), pageable);

        List<ReviewHomeResponse> homeResponses = reviews.stream()
                .map(ReviewHomeResponse::from)
                .toList();

        return ReviewHomeResponseList.from(homeResponses);
    }

    /**
     * 복습 업로드
     */
    @Transactional
    public void uploadReview(Long userId, MultipartFile multipartFile, ReviewUploadRequest request) {
        log.info("[ReviewService.uploadReview]");

        User user = userService.findUser(userId);
        Review review = request.toReview(user);


        if (multipartFile != null && !multipartFile.isEmpty()) {
            String folderName = "goat";
            ImageInfo imageInfo = s3Uploader.upload(multipartFile, folderName);
            review.setImageInfo(imageInfo);
        }
        reviewRepository.save(review);

        if(review.getIsAutoRepeat() || review.getIsRepeatable() || !review.getReviewDates().isEmpty()) {
            registerNotification(review);
        }
    }

    /**
     * 복습 업로드 시 알림 등록
     */
    private void registerNotification(Review review) {

        if(review.getIsAutoRepeat()) {
            try {
                schedulerConfiguration.scheduleAutoReviewNotification(review);
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
        }

        for (ReviewDate date : review.getReviewDates()) {
            try {
                schedulerConfiguration.scheduleCustomReviewNotification(review, date);
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 복습 자료 한 개 상세 보기
     */
    public ReviewDetailResponse getDetailReview(Long userId, Long reviewId) {
        Review review = reviewRepository.findByIdAndUser_UserId(reviewId, userId)
                .orElseThrow(() -> new ReviewNotFoundException(ReviewErrorCode.REVIEW_NOT_FOUND));

        return ReviewDetailResponse.from(review);
    }

    /**
     * 복습 자료 수정
     */
    @Transactional
    public void updateReview(Long reviewId, MultipartFile multipartFile, ReviewUpdateRequest reviewUpdateRequest) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(ReviewErrorCode.REVIEW_NOT_FOUND));

        ImageInfo imageInfo = review.getImageInfo();

        if (multipartFile != null && !multipartFile.isEmpty()) {
            if (imageInfo != null) {
                s3Uploader.deleteImage(review.getImageInfo());
                String folderName = "goat";
                imageInfo = s3Uploader.upload(multipartFile, folderName);
            } else {
                String folderName = "goat";
                imageInfo = s3Uploader.upload(multipartFile, folderName);
            }
        }

        review.updateReview(reviewUpdateRequest, imageInfo);
    }

    /**
     * 복습 자료 한 개 삭제
     */
    @Transactional
    public void deleteReview(Long userId, Long reviewId){
        User user = userService.findUser(userId);
        Review review = reviewRepository.findByIdAndUser_UserId(reviewId, userId)
                .orElseThrow(() -> new ReviewNotFoundException(ReviewErrorCode.REVIEW_NOT_FOUND));

        s3Uploader.deleteImage(review.getImageInfo());
        reviewRepository.delete(review);
    }
}