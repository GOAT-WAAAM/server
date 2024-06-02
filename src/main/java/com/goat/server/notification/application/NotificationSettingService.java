package com.goat.server.notification.application;

import com.goat.server.notification.domain.NotificationSetting;
import com.goat.server.notification.dto.CommentNotiSettingRequest;
import com.goat.server.notification.dto.NotificationSettingResponse;
import com.goat.server.notification.dto.PostNotiSettingRequest;
import com.goat.server.notification.dto.ReviewNotiSettingRequest;
import com.goat.server.notification.repository.NotificationSettingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class NotificationSettingService {

    private final NotificationSettingRepository notificationSettingRepository;

    /**
     * 알림 권한 설정 보기
     */
    public NotificationSettingResponse getNotificationSetting(Long userId) {
        NotificationSetting notificationSetting = notificationSettingRepository.findByUserUserId(userId);
        return new NotificationSettingResponse(
                notificationSetting.getIsReviewNoti(),
                notificationSetting.getIsPostNoti(),
                notificationSetting.getIsCommentNoti()
        );
    }

    /**
     * 복습 알림 권한 변경하기
     */
    @Transactional
    public void updateReviewNotiSetting(Long userId, ReviewNotiSettingRequest request) {
        NotificationSetting notificationSetting = notificationSettingRepository.findByUserUserId(userId);
        notificationSetting.updateReviewNoti(request.isReviewNoti());
        notificationSettingRepository.save(notificationSetting);
    }

    /**
     * 댓글 알림 권한 변경하기
     */
    @Transactional
    public void updateCommentNotiSetting(Long userId, CommentNotiSettingRequest request) {
        NotificationSetting notificationSetting = notificationSettingRepository.findByUserUserId(userId);
        notificationSetting.updateCommentNoti(request.isCommentNoti());
        notificationSettingRepository.save(notificationSetting);
    }

    /**
     * 게시글 알림 권한 변경하기
     */
    @Transactional
    public void updatePostNotiSetting(Long userId, PostNotiSettingRequest request) {
        NotificationSetting notificationSetting = notificationSettingRepository.findByUserUserId(userId);
        notificationSetting.updatePostNoti(request.isPostNoti());
        notificationSettingRepository.save(notificationSetting);
    }
}
