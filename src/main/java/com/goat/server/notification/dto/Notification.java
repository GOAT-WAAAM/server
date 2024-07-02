package com.goat.server.notification.dto;

import lombok.Builder;

@Builder
public record Notification(
        String title,
        String body,
        String image
) {
}
