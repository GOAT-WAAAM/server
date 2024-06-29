package com.goat.server.review.fixture;

import com.goat.server.mypage.fixture.UserFixture;
import com.goat.server.review.domain.StarReview;
import org.springframework.test.util.ReflectionTestUtils;

public class StarReviewFixture {

    public static StarReview DUMMY_STARREVIEW2 = StarReview.builder()
            .review(ReviewFixture.DUMMY_REVIEW2)
            .user(UserFixture.USER_USER)
            .build();

    static {
        ReflectionTestUtils.setField(DUMMY_STARREVIEW2, "id", 2L);
    }
}
