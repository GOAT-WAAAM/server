package com.goat.server.notification.dto.fcm;

import lombok.Builder;

@Builder
public record Notification(
        String title,
        String body,
        String image
) {
}
