package com.goat.server.notification.dto;

import lombok.Builder;

@Builder
public record FCMSendDto(
         String token,
         String title,
         String body
) {
}
