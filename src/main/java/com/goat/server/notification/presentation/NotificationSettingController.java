package com.goat.server.notification.presentation;

import com.goat.server.global.dto.ResponseTemplate;
import com.goat.server.notification.application.NotificationSettingService;
import com.goat.server.notification.dto.request.NotificationSettingRequest;
import com.goat.server.notification.dto.response.NotificationSettingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "NotificationSettingController", description = "NotificationSettingController 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/goat/mypage/notification/setting")
public class NotificationSettingController {

    private final NotificationSettingService notificationSettingService;

    @Operation(summary = "알림 권한들 보기", description = "알림 권한들 보기")
    @GetMapping
    public ResponseEntity<ResponseTemplate<Object>> getNotificationSetting(@AuthenticationPrincipal Long userId) {

        NotificationSettingResponse notificationSetting = notificationSettingService.getNotificationSetting(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(notificationSetting));
    }

    @Operation(summary = "복습 알림 권한 변경하기", description = "복습 알림 권한 변경하기")
    @PutMapping
    public ResponseEntity<ResponseTemplate<Object>> updateNotificationSetting(
            @AuthenticationPrincipal Long userId,
            @RequestBody NotificationSettingRequest request) {

        notificationSettingService.updateNotificationSetting(userId, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }
}
