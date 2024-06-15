package com.goat.server.review.repository.init;

import com.goat.server.global.util.LocalDummyDataInit;
import com.goat.server.mypage.domain.User;
import com.goat.server.mypage.exception.UserNotFoundException;
import com.goat.server.mypage.exception.errorcode.MypageErrorCode;
import com.goat.server.mypage.repository.UserRepository;
import com.goat.server.review.domain.Review;
import com.goat.server.review.domain.ReviewDate;
import com.goat.server.review.domain.type.Date;
import com.goat.server.review.exception.ReviewNotFoundException;
import com.goat.server.review.exception.errorcode.ReviewErrorCode;
import com.goat.server.review.repository.ReviewDateRepository;
import com.goat.server.review.repository.ReviewRepository;
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
@Order(4)
public class ReviewDateInitializer implements ApplicationRunner {

    private final ReviewDateRepository reviewDateRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (reviewDateRepository.count() > 0) {
            log.info("[ReviewDate] 더미 데이터 존재");
        } else {
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

            List<ReviewDate> reviewDates = new ArrayList<>();

            ReviewDate DUMMY_REVIEW1_DAY1 = ReviewDate.builder()
                    .review(review1)
                    .date(Date.MON)
                    .build();
            ReviewDate DUMMY_REVIEW1_DAY2 = ReviewDate.builder()
                    .review(review1)
                    .date(Date.WED)
                    .build();
            ReviewDate DUMMY_REVIEW1_DAY3 = ReviewDate.builder()
                    .review(review1)
                    .date(Date.FRI)
                    .build();

            ReviewDate DUMMY_REVIEW2_DAY1 = ReviewDate.builder()
                    .review(review2)
                    .date(Date.TUE)
                    .build();
            ReviewDate DUMMY_REVIEW2_DAY2 = ReviewDate.builder()
                    .review(review2)
                    .date(Date.THU)
                    .build();
            ReviewDate DUMMY_REVIEW2_DAY3 = ReviewDate.builder()
                    .review(review2)
                    .date(Date.SAT)
                    .build();

            ReviewDate DUMMY_REVIEW3_DAY1 = ReviewDate.builder()
                    .review(review3)
                    .date(Date.MON)
                    .build();
            ReviewDate DUMMY_REVIEW3_DAY2 = ReviewDate.builder()
                    .review(review3)
                    .date(Date.TUE)
                    .build();

            ReviewDate DUMMY_REVIEW4_DAY1 = ReviewDate.builder()
                    .review(review4)
                    .date(Date.SUN)
                    .build();

            ReviewDate DUMMY_REVIEW5_DAY1 = ReviewDate.builder()
                    .review(review5)
                    .date(Date.SUN)
                    .build();
            ReviewDate DUMMY_REVIEW6_DAY1 = ReviewDate.builder()
                    .review(review6)
                    .date(Date.SUN)
                    .build();



            reviewDates.add(DUMMY_REVIEW1_DAY1);
            reviewDates.add(DUMMY_REVIEW1_DAY2);
            reviewDates.add(DUMMY_REVIEW1_DAY3);

            reviewDates.add(DUMMY_REVIEW2_DAY1);
            reviewDates.add(DUMMY_REVIEW2_DAY2);
            reviewDates.add(DUMMY_REVIEW2_DAY3);

            reviewDates.add(DUMMY_REVIEW3_DAY1);
            reviewDates.add(DUMMY_REVIEW3_DAY2);

            reviewDates.add(DUMMY_REVIEW4_DAY1);

            reviewDates.add(DUMMY_REVIEW5_DAY1);

            reviewDates.add(DUMMY_REVIEW6_DAY1);

            reviewDateRepository.saveAll(reviewDates);
        }
    }
}
