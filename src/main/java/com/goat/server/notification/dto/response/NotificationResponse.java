package com.goat.server.notification.dto.response;

import com.goat.server.notification.domain.Notification;
import lombok.Builder;

import java.util.List;

@Builder
public record NotificationResponse(
        List<NotificationComponentResponse> notifications
) {
    @Builder
    public record NotificationComponentResponse(
            Long reviewId,
            String reviewBody,
            Boolean isRead
    ){
        public static NotificationComponentResponse from(Notification notification) {
            return NotificationComponentResponse.builder()
                    .reviewId(notification.getReview().getId())
                    .reviewBody(notification.getContent())
                    .isRead(notification.getIsRead())
                    .build();
        }
    }

    public static NotificationResponse from(List<NotificationComponentResponse> notifications) {
        return NotificationResponse.builder()
                .notifications(notifications)
                .build();
    }
}
