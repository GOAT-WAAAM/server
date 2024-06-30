package com.goat.server.review.repository.init;

import com.goat.server.directory.domain.Directory;
import com.goat.server.directory.repository.DirectoryRepository;
import com.goat.server.global.domain.ImageInfo;
import com.goat.server.global.util.LocalDummyDataInit;
import com.goat.server.mypage.domain.User;
import com.goat.server.mypage.exception.UserNotFoundException;
import com.goat.server.mypage.exception.errorcode.MypageErrorCode;
import com.goat.server.mypage.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (reviewRepository.count() > 0) {
            log.info("[Review] 더미 데이터 존재");
        } else {
            Directory dummyTrashDirectory = directoryRepository.findById(2L).orElseThrow();
            Directory dummyParentDirectory = directoryRepository.findById(4L).orElseThrow();
            Directory dummyChildDirectory = directoryRepository.findById(7L).orElseThrow();

            User user = userRepository.findByEmail("userEmail")
                    .orElseThrow(() -> new UserNotFoundException(MypageErrorCode.USER_NOT_FOUND));

            List<Review> reviewList = new ArrayList<>();

            Review DUMMY_REVIEW1 = Review.builder()
                    .title("this is title")
                    .content("content")
                    .imageInfo(ImageInfo.builder()
                            .imageFileName("라이언1.PNG")
                            .imageFolderName("goat/")
                            .imageUrl("https://team-goat")
                            .build())
                    .isRepeatable(true)
                    .isAutoRepeat(true)
                    .isPostShare(true)
                    .user(user)
                    .directory(dummyTrashDirectory)
                    .reviewCnt(0L)
                    .build();

            Review DUMMY_REVIEW2 = Review.builder()
                    .title("this is title")
                    .content("content")
                    .imageInfo(ImageInfo.builder()
                            .imageFileName("라이언2.PNG")
                            .imageFolderName("goat/")
                            .imageUrl("https://team-goat")
                            .build())
                    .isRepeatable(true)
                    .isAutoRepeat(false)
                    .isPostShare(true)
                    .user(user)
                    .directory(dummyParentDirectory)
                    .reviewCnt(1L)
                    .build();

            Review DUMMY_REVIEW3 = Review.builder()
                    .title("this is title")
                    .content("content")
                    .imageInfo(ImageInfo.builder()
                            .imageFileName("라이언3.PNG")
                            .imageFolderName("goat/")
                            .imageUrl("https://team-goat")
                            .build())
                    .isRepeatable(true)
                    .isAutoRepeat(false)
                    .isPostShare(true)
                    .user(user)
                    .directory(dummyParentDirectory)
                    .reviewCnt(2L)
                    .build();

            Review DUMMY_REVIEW4 = Review.builder()
                    .title("this is title")
                    .content("content")
                    .imageInfo(ImageInfo.builder()
                            .imageFileName("라이언4.PNG")
                            .imageFolderName("goat/")
                            .imageUrl("https://team-goat")
                            .build())
                    .isRepeatable(true)
                    .isAutoRepeat(false)
                    .isPostShare(true)
                    .user(user)
                    .directory(dummyParentDirectory)
                    .reviewCnt(4L)
                    .build();

            Review DUMMY_REVIEW5 = Review.builder()
                    .title("this is title")
                    .content("content")
                    .imageInfo(ImageInfo.builder()
                            .imageFileName("라이언5.PNG")
                            .imageFolderName("goat/")
                            .imageUrl("https://team-goat")
                            .build())
                    .isRepeatable(true)
                    .isAutoRepeat(false)
                    .isPostShare(true)
                    .user(user)
                    .directory(dummyChildDirectory)
                    .reviewCnt(3L)
                    .build();

            Review DUMMY_REVIEW6 = Review.builder()
                    .title("this is title")
                    .content("content")
                    .imageInfo(ImageInfo.builder()
                            .imageFileName("라이언6.PNG")
                            .imageFolderName("goat/")
                            .imageUrl("https://team-goat")
                            .build())
                    .isRepeatable(true)
                    .isAutoRepeat(false)
                    .isPostShare(true)
                    .user(user)
                    .directory(dummyChildDirectory)
                    .reviewCnt(6L)
                    .build();

            Review DUMMY_REVIEW7 = Review.builder()
                    .title("this is title")
                    .content("content")
                    .isRepeatable(true)
                    .isAutoRepeat(false)
                    .isPostShare(true)
                    .user(user)
                    .directory(dummyChildDirectory)
                    .reviewCnt(8L)
                    .build();

            Review DUMMY_REVIEW8 = Review.builder()
                    .title("this is title")
                    .content("content")
                    .isRepeatable(true)
                    .isAutoRepeat(false)
                    .isPostShare(true)
                    .user(user)
                    .directory(dummyChildDirectory)
                    .reviewCnt(9L)
                    .build();

            Review DUMMY_REVIEW9 = Review.builder()
                    .title("this is title")
                    .content("content")
                    .isRepeatable(true)
                    .isAutoRepeat(false)
                    .isPostShare(true)
                    .user(user)
                    .directory(dummyChildDirectory)
                    .reviewCnt(10L)
                    .build();

            reviewList.add(DUMMY_REVIEW1);
            reviewList.add(DUMMY_REVIEW2);
            reviewList.add(DUMMY_REVIEW3);
            reviewList.add(DUMMY_REVIEW4);
            reviewList.add(DUMMY_REVIEW5);
            reviewList.add(DUMMY_REVIEW6);
            reviewList.add(DUMMY_REVIEW7);
            reviewList.add(DUMMY_REVIEW8);
            reviewList.add(DUMMY_REVIEW9);

            reviewRepository.saveAll(reviewList);
        }
    }
}
