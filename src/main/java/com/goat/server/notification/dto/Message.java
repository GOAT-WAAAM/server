package com.goat.server.notification.dto;

import lombok.Builder;

@Builder
public record Message(
        Notification notification,
        String token
) {
}
