package com.goat.server.notification.application;

import com.goat.server.mypage.domain.User;
import com.goat.server.mypage.exception.UserNotFoundException;
import com.goat.server.mypage.repository.UserRepository;
import com.goat.server.notification.domain.NotificationSetting;
import com.goat.server.notification.dto.request.NotificationSettingRequest;
import com.goat.server.notification.dto.response.NotificationSettingResponse;
import com.goat.server.notification.repository.NotificationSettingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.goat.server.mypage.exception.errorcode.MypageErrorCode.USER_NOT_FOUND;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class NotificationSettingService {

    private final NotificationSettingRepository notificationSettingRepository;
    private final UserRepository userRepository;

    /**
     * 알림 권한 설정 보기
     */
    public NotificationSettingResponse getNotificationSetting(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        NotificationSetting notificationSetting = notificationSettingRepository.findByUserUserId(userId);

        return NotificationSettingResponse.from(notificationSetting);
    }

    /**
     * 복습 알림 권한 변경하기
     */
    @Transactional
    public void updateNotificationSetting(Long userId, NotificationSettingRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        NotificationSetting notificationSetting = notificationSettingRepository.findByUserUserId(userId);
        notificationSetting.updateReviewNoti(request.isReviewNoti());
        notificationSettingRepository.save(notificationSetting);
    }
}
