package com.goat.server.notification.fixture;

import com.goat.server.mypage.fixture.UserFixture;
import com.goat.server.notification.domain.Notification;
import com.goat.server.review.fixture.ReviewFixture;
import org.springframework.test.util.ReflectionTestUtils;

public class NotificationFixture {

    public static final Notification DUMMY_NOTIFICATION1 = Notification.builder()
            .user(UserFixture.USER_USER)
            .review(ReviewFixture.DUMMY_REVIEW2)
            .build();

    public static final Notification DUMMY_NOTIFICATION2 = Notification.builder()
            .user(UserFixture.USER_USER)
            .review(ReviewFixture.DUMMY_REVIEW1)
            .build();

    public static final Notification DUMMY_NOTIFICATION3 = Notification.builder()
            .user(UserFixture.USER_USER)
            .review(ReviewFixture.DUMMY_REVIEW3)
            .build();

    static {
        ReflectionTestUtils.setField(DUMMY_NOTIFICATION1, "noti_id", 1L);
        ReflectionTestUtils.setField(DUMMY_NOTIFICATION2, "noti_id", 2L);
        ReflectionTestUtils.setField(DUMMY_NOTIFICATION3, "noti_id", 3L);
    }
}
