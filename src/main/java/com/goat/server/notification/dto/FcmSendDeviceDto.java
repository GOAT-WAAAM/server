package com.goat.server.notification.dto;

import lombok.Builder;

@Builder
public record FcmSendDeviceDto(
        String deviceToken
) {
}
