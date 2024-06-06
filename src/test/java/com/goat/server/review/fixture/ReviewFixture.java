package com.goat.server.review.fixture;

import com.goat.server.directory.fixture.DirectoryFixture;
import com.goat.server.review.domain.Review;
import org.springframework.test.util.ReflectionTestUtils;

public class ReviewFixture {

    public static Review DUMMY_REVIEW1 = Review.builder()
            .directory(DirectoryFixture.PARENT_DIRECTORY1)
            .imageTitle("imageTitle1")
            .build();
    public static Review DUMMY_REVIEW2 = Review.builder()
            .directory(DirectoryFixture.PARENT_DIRECTORY1)
            .imageTitle("imageTitle2")
            .build();

    static {
        ReflectionTestUtils.setField(DUMMY_REVIEW1, "reviewId", 1L);
        ReflectionTestUtils.setField(DUMMY_REVIEW2, "reviewId", 2L);
    }
}
