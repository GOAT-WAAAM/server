package com.goat.server.notification.presentation;

import com.goat.server.global.dto.ResponseTemplate;
import com.goat.server.mypage.dto.GoalRequest;
import com.goat.server.notification.application.NotificationSettingService;
import com.goat.server.notification.dto.CommentNotiSettingRequest;
import com.goat.server.notification.dto.NotificationSettingResponse;
import com.goat.server.notification.dto.PostNotiSettingRequest;
import com.goat.server.notification.dto.ReviewNotiSettingRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "NotiSettingController", description = "NotiSettingController 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/goat/mypage/notifications/setting")
public class NotificationSettingController {

    private final NotificationSettingService notificationSettingService;

    @Operation(summary = "알림 권한들 보기", description = "알림 권한들 보기")
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseTemplate<Object>> getNotificationSetting(@PathVariable Long userId) {
        NotificationSettingResponse notificationSetting = notificationSettingService.getNotificationSetting(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(notificationSetting));
    }

    @Operation(summary = "복습 알림 권한 변경하기", description = "복습 알림 권한 변경하기")
    @PutMapping("/review-noti/{userId}")
    public ResponseEntity<ResponseTemplate<Object>> updateReviewNotiSetting(@PathVariable Long userId,
                                                                               @RequestBody ReviewNotiSettingRequest request) {
        notificationSettingService.updateReviewNotiSetting(userId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

    @Operation(summary = "댓글 알림 권한 변경하기", description = "댓글 알림 권한 변경하기")
    @PutMapping("/comment-noti/{userId}")
    public ResponseEntity<ResponseTemplate<Object>> updateCommentNotiSetting(@PathVariable Long userId,
                                                          @RequestBody CommentNotiSettingRequest request) {
        notificationSettingService.updateCommentNotiSetting(userId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

    @Operation(summary = "게시글 알림 권한 변경하기", description = "게시글 알림 권한 변경하기")
    @PutMapping("/post-noti/{userId}")
    public ResponseEntity<ResponseTemplate<Object>> updatePostNotiSetting(@PathVariable Long userId,
                                                                               @RequestBody PostNotiSettingRequest request) {
        notificationSettingService.updatePostNotiSetting(userId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }
}
