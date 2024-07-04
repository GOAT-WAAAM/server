package com.goat.server.notification.dto.fcm;

import lombok.Builder;

@Builder
public record Message(
        Notification notification,
        String token,
        Data data
) {
}
