package com.goat.server.review.application;

import com.goat.server.notification.application.NotificationService;
import com.goat.server.review.dto.response.MissedReviewResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.goat.server.mypage.fixture.UserFixture.USER_USER;
import static com.goat.server.notification.fixture.NotificationFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private NotificationService notificationService;

    @Test
    @DisplayName("놓친 복습 조회 테스트")
    void getMissedReviewTest() {
        //given
        given(notificationService.findAllByUserId(USER_USER.getUserId())).willReturn(List.of(DUMMY_NOTIFICATION1, DUMMY_NOTIFICATION2, DUMMY_NOTIFICATION3));

        //when
        MissedReviewResponse response = reviewService.getMissedReview(USER_USER.getUserId());

        //then
        assertThat(response.reviewSimpleResponses()).hasSize(3);
    }
}
