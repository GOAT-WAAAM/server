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
}

