package com.goat.server.notification.application;

import com.goat.server.global.exception.AccessDeniedException;
import com.goat.server.mypage.domain.User;
import com.goat.server.mypage.exception.UserNotFoundException;
import com.goat.server.mypage.repository.UserRepository;
import com.goat.server.notification.domain.Notification;
import com.goat.server.notification.dto.response.NotificationResponse;
import com.goat.server.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.goat.server.global.exception.errorcode.GlobalErrorCode.ACCESS_DENIED;
import static com.goat.server.mypage.exception.errorcode.MypageErrorCode.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveNotification(Notification notification) {

        log.info("[NotificationService.saveNotification]");

        notificationRepository.save(notification);
    }

    public NotificationResponse getNotifications(Long userId) {

            log.info("[NotificationService.getNotifications]");

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

            List<Notification> notifications = notificationRepository.findAllByUser(user);

            List<NotificationResponse.NotificationComponentResponse> notificationComponents = notifications.stream()
                    .map(NotificationResponse.NotificationComponentResponse::from)
                    .toList();

            return NotificationResponse.from(notificationComponents);
    }

    @Transactional
    public void readNotification(Long userId, Long notificationId) {

        log.info("[NotificationService.readNotification]");

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found"));

        if (!notification.getUser().getUserId().equals(userId)) {
            throw new AccessDeniedException(ACCESS_DENIED);
        }

        notification.read();

        notificationRepository.save(notification);
    }
}
