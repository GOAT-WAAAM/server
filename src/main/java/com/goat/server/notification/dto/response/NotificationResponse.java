package com.goat.server.notification.dto.response;

import com.goat.server.notification.domain.Notification;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Builder
public record NotificationResponse(
        List<NotificationComponentResponse> notifications
) {
    @Builder
    public record NotificationComponentResponse(
            Long notificationId,
            Long reviewId,
            String reviewBody,
            String whenItPushed,
            Boolean isRead
    ){
        public static NotificationComponentResponse from(Notification notification) {

            String whenItPushed = calculateWhenItPushed(notification);

            return NotificationComponentResponse.builder()
                    .notificationId(notification.getNoti_id())
                    .reviewId(notification.getReview().getId())
                    .reviewBody(notification.getContent())
                    .whenItPushed(whenItPushed)
                    .isRead(notification.getIsRead())
                    .build();
        }

        private static String calculateWhenItPushed(Notification notification) {
            LocalDateTime currentDateTime = LocalDateTime.now();
            LocalDateTime createdDateTime = notification.getCreatedDate();
            long hoursBetween = ChronoUnit.HOURS.between(createdDateTime, currentDateTime);
            long daysBetween = ChronoUnit.DAYS.between(createdDateTime.toLocalDate(), currentDateTime.toLocalDate());

            String whenItPushed = null;

            if (daysBetween == 0) {
                if(hoursBetween == 0) {
                    whenItPushed = "방금 전";
                } else {
                    whenItPushed = hoursBetween + "시간 전";
                }
            } else {
                whenItPushed = daysBetween + "일 전";
            }
            return whenItPushed;
        }
    }

    public static NotificationResponse from(List<NotificationComponentResponse> notifications) {
        return NotificationResponse.builder()
                .notifications(notifications)
                .build();
    }


}
