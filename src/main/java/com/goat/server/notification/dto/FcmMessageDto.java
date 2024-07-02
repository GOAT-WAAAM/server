package com.goat.server.notification.dto;

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
}

@Builder
record Message(
        Notification notification,
        String token
) {
}

@Builder
record Notification(
        String title,
        String body,
        String image
) {
}
