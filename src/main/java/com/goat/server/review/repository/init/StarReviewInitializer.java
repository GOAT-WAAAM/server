package com.goat.server.review.repository.init;

import com.goat.server.global.util.LocalDummyDataInit;
import com.goat.server.mypage.domain.User;
import com.goat.server.mypage.exception.UserNotFoundException;
import com.goat.server.mypage.exception.errorcode.MypageErrorCode;
import com.goat.server.mypage.repository.UserRepository;
import com.goat.server.review.domain.Review;
import com.goat.server.review.domain.StarReview;
import com.goat.server.review.exception.ReviewNotFoundException;
import com.goat.server.review.exception.errorcode.ReviewErrorCode;
import com.goat.server.review.repository.ReviewRepository;
import com.goat.server.review.repository.StarReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@LocalDummyDataInit
@Order(5)
public class StarReviewInitializer implements ApplicationRunner {

    private final StarReviewRepository starReviewRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) {

        if (starReviewRepository.count() > 0) {
            log.info("[Star] 더미 데이터 존재");
        } else {
            User user = userRepository.findByEmail("userEmail")
                    .orElseThrow(() -> new UserNotFoundException(MypageErrorCode.USER_NOT_FOUND));

            Review review1 = reviewRepository.findById(1L)
                    .orElseThrow(() -> new ReviewNotFoundException(ReviewErrorCode.REVIEW_NOT_FOUND));

            Review review2 = reviewRepository.findById(2L)
                    .orElseThrow(() -> new ReviewNotFoundException(ReviewErrorCode.REVIEW_NOT_FOUND));

            Review review3 = reviewRepository.findById(3L)
                    .orElseThrow(() -> new ReviewNotFoundException(ReviewErrorCode.REVIEW_NOT_FOUND));

            Review review4 = reviewRepository.findById(4L)
                    .orElseThrow(() -> new ReviewNotFoundException(ReviewErrorCode.REVIEW_NOT_FOUND));

            Review review5 = reviewRepository.findById(5L)
                    .orElseThrow(() -> new ReviewNotFoundException(ReviewErrorCode.REVIEW_NOT_FOUND));

            Review review6 = reviewRepository.findById(6L)
                    .orElseThrow(() -> new ReviewNotFoundException(ReviewErrorCode.REVIEW_NOT_FOUND));

            List<StarReview> starList = new ArrayList<>();

            StarReview starReview1 = StarReview.builder()
                    .user(user)
                    .review(review1)
                    .build();
            StarReview starReview2 = StarReview.builder()
                    .user(user)
                    .review(review2)
                    .build();
            StarReview starReview3 = StarReview.builder()
                    .user(user)
                    .review(review3)
                    .build();
            StarReview starReview4 = StarReview.builder()
                    .user(user)
                    .review(review4)
                    .build();
            StarReview starReview5 = StarReview.builder()
                    .user(user)
                    .review(review5)
                    .build();
            StarReview starReview6 = StarReview.builder()
                    .user(user)
                    .review(review6)
                    .build();

            starList.add(starReview1);
            starList.add(starReview2);
            starList.add(starReview3);
            starList.add(starReview4);
            starList.add(starReview5);
            starList.add(starReview6);

            starReviewRepository.saveAll(starList);
        }
    }
}
