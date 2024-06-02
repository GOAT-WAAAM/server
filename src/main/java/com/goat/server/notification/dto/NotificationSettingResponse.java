package com.goat.server.notification.dto;

public record NotificationSettingResponse(
        Boolean isReviewNoti,
        Boolean isPostNoti,
        Boolean isCommentNoti
) {
}
