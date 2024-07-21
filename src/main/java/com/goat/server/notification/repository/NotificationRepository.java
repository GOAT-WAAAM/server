package com.goat.server.notification.repository;

import com.goat.server.mypage.domain.User;
import com.goat.server.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByUser(User user);

    @Query("SELECT n FROM Notification n JOIN FETCH n.review WHERE n.user.userId = :userId AND n.isRead = :isRead")
    List<Notification> findAllByUserIdAndIsRead(Long userId, Boolean isRead);
}
