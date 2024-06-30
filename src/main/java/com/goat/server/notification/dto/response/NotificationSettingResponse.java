package com.goat.server.notification.dto.response;

import com.goat.server.notification.domain.NotificationSetting;
import lombok.Builder;

@Builder
public record NotificationSettingResponse(
        Boolean isReviewNoti
) {
    public static NotificationSettingResponse from(NotificationSetting setting) {
        return NotificationSettingResponse.builder()
                .isReviewNoti(setting.getIsReviewNoti())
                .build();
    }
}
