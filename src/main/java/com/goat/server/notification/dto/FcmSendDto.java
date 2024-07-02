package com.goat.server.notification.dto;

import lombok.Builder;

@Builder
public record FcmSendDto(
         String token,
         String title,
         String body
) {
}
