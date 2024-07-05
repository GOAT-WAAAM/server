package com.goat.server.notification.presentation;

import com.goat.server.global.dto.ResponseTemplate;
import com.goat.server.notification.application.NotificationService;
import com.goat.server.notification.dto.response.NotificationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "NotificationController", description = "NotificationController 관련 API")
@RequestMapping("/goat/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "알림 목록 보기", description = "알림 목록 보기")
    @GetMapping
    public ResponseEntity<ResponseTemplate<Object>> getNotifications(@AuthenticationPrincipal Long userId) {

        log.info("[NotificationController.getNotifications]");

        NotificationResponse notification = notificationService.getNotifications(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(notification));
    }
}
