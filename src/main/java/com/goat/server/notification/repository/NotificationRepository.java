package com.goat.server.notification.repository;

import com.goat.server.mypage.domain.User;
import com.goat.server.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByUser(User user);

    List<Notification> findAllByUserAndIsRead(User user, Boolean isRead);
}
