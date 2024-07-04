package com.goat.server.notification.dto.fcm;

import lombok.Builder;

@Builder
public record Data(
        String reviewId
) {
}
