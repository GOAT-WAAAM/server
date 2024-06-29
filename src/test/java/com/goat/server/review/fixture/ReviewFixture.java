package com.goat.server.review.fixture;

import com.goat.server.directory.fixture.DirectoryFixture;
import com.goat.server.global.domain.ImageInfo;
import com.goat.server.review.domain.Review;
import org.springframework.test.util.ReflectionTestUtils;

public class ReviewFixture {

    public static Review DUMMY_REVIEW1 = Review.builder()
            .directory(DirectoryFixture.PARENT_DIRECTORY1)
            .title("imageTitle1")
            .content("imageContent1")
            .imageInfo(new ImageInfo("review1", "goat", "http://goat.goat.com1"))
            .build();
    public static Review DUMMY_REVIEW2 = Review.builder()
            .directory(DirectoryFixture.PARENT_DIRECTORY1)
            .title("imageTitle2")
            .imageInfo(new ImageInfo("review2", "goat", "http://goat.goat.com2"))
            .build();

    static {
        ReflectionTestUtils.setField(DUMMY_REVIEW1, "id", 1L);
        ReflectionTestUtils.setField(DUMMY_REVIEW2, "id", 2L);
    }
}
