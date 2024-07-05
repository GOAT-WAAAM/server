package com.goat.server.notification.application;

import com.goat.server.notification.domain.Notification;
import com.goat.server.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public void saveNotification(Notification notification) {

        log.info("[NotificationService.saveNotification]");

        notificationRepository.save(notification);
    }
}
