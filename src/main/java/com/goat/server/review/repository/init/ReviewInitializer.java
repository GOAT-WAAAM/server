package com.goat.server.review.repository.init;

import com.goat.server.directory.domain.Directory;
import com.goat.server.directory.exception.DirectoryNotFoundException;
import com.goat.server.directory.exception.errorcode.DirectoryErrorCode;
import com.goat.server.directory.repository.DirectoryRepository;
import com.goat.server.global.util.LocalDummyDataInit;
import com.goat.server.review.domain.Review;
import com.goat.server.review.repository.ReviewRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

@Slf4j
@RequiredArgsConstructor
@LocalDummyDataInit
@Order(3)
public class ReviewInitializer implements ApplicationRunner {

    private final DirectoryRepository directoryRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (reviewRepository.count() > 0) {
            log.info("[Review]더미 데이터 존재");
        } else {
            Directory dummyTrashDirectory = directoryRepository.findById(2L)
                    .orElseThrow(() -> new DirectoryNotFoundException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));
            Directory dummyParentDirectory = directoryRepository.findById(4L)
                    .orElseThrow(() -> new DirectoryNotFoundException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));
            Directory dummyChildDirectory = directoryRepository.findById(7L)
                    .orElseThrow(() -> new DirectoryNotFoundException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));

            List<Review> reviewList = new ArrayList<>();

            Review review1 = Review.builder()
                    .title("review1")
                    .content("review1 content")
                    .directory(dummyTrashDirectory)
                    .build();

            Review review2 = Review.builder()
                    .title("review2")
                    .content("review2 content")
                    .directory(dummyParentDirectory)
                    .build();
            Review review3 = Review.builder()
                    .title("review3")
                    .content("review3 content")
                    .directory(dummyParentDirectory)
                    .build();

            Review review4 = Review.builder()
                    .title("review4")
                    .content("review4 content")
                    .directory(dummyChildDirectory)
                    .build();
            Review review5 = Review.builder()
                    .title("review5")
                    .content("review5 content")
                    .directory(dummyChildDirectory)
                    .build();
            Review review6 = Review.builder()
                    .title("review6")
                    .content("review6 content")
                    .directory(dummyChildDirectory)
                    .build();

            reviewList.add(review1);
            reviewList.add(review2);
            reviewList.add(review3);
            reviewList.add(review4);
            reviewList.add(review5);
            reviewList.add(review6);

            reviewRepository.saveAll(reviewList);
        }
    }
}
