package com.goat.server.notification.dto.fcm;

import lombok.Builder;

@Builder
public record FcmSendDto(
         String token,
         String title,
         String body
) {
}
