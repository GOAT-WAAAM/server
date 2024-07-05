package com.goat.server.notification.dto.fcm;

import lombok.Builder;

/**
 * FCM 전송 Format DTO
 *
 */

@Builder
public record FcmMessageDto(
        boolean validateOnly,
        Message message
) {
    @Builder
    public record Message(
            Notification notification,
            String token,
            Data data
    ) {
        @Builder
        public record Notification(
                String title,
                String body,
                String image
        ) {
        }

        @Builder
        public record Data(
                String reviewId
        ) {
        }

    }
}

