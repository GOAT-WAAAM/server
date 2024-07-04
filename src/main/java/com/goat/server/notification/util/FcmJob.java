package com.goat.server.notification.util;

import com.goat.server.mypage.domain.User;
import com.goat.server.mypage.exception.UserNotFoundException;
import com.goat.server.mypage.repository.UserRepository;
import com.goat.server.notification.application.FcmService;
import com.goat.server.notification.application.NotificationService;
import com.goat.server.notification.domain.Notification;
import com.goat.server.notification.domain.NotificationSetting;
import com.goat.server.notification.domain.type.PushType;
import com.goat.server.notification.exception.PushPermissionDeniedException;
import com.goat.server.notification.repository.NotificationSettingRepository;
import com.goat.server.review.domain.Review;
import com.goat.server.review.exception.ReviewNotFoundException;
import com.goat.server.review.repository.ReviewRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.goat.server.mypage.exception.errorcode.MypageErrorCode.USER_NOT_FOUND;
import static com.goat.server.notification.exception.errorcode.NotificationErrorCode.PUSH_DENIED;
import static com.goat.server.review.exception.errorcode.ReviewErrorCode.REVIEW_NOT_FOUND;

/**
 * FCM 메시지 전송을 위한 Job
 *
 */
@Slf4j
@NoArgsConstructor
@Component
public class FcmJob implements Job {

    private static FcmService fcmService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {

        if (fcmService == null) {
            ApplicationContext appCtx = (ApplicationContext) jobExecutionContext.getJobDetail().getJobDataMap().get("appContext");
            fcmService = appCtx.getBean(FcmService.class);
        }

        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        ApplicationContext applicationContext = (ApplicationContext) dataMap.get("appContext");

        Long reviewId = dataMap.getLong("reviewId");
        String pushType = (String) dataMap.get("pushType");
        String content = (String) dataMap.get("content");

        try {
            sendMessageToClient(applicationContext, reviewId, pushType, content);
        } catch (Exception e) {
            log.error("FCM 전송 실패", e);
        }
    }

    private static void sendMessageToClient(ApplicationContext applicationContext, Long reviewId, String pushType, String content) throws IOException {

        ReviewRepository reviewRepository = applicationContext.getBean(ReviewRepository.class);
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException(REVIEW_NOT_FOUND));

        UserRepository userRepository = applicationContext.getBean(UserRepository.class);
        User user = userRepository.findById(review.getUser().getUserId()).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        NotificationSettingRepository notificationSettingRepository = applicationContext.getBean(NotificationSettingRepository.class);
        NotificationSetting notificationSetting = notificationSettingRepository.findByUserUserId(user.getUserId());

        if (!notificationSetting.getIsReviewNoti()) {
            throw new PushPermissionDeniedException(PUSH_DENIED);
        }

        Notification notification = Notification.builder()
                .content(content)
                .user(user)
                .review(review)
                .build();

        NotificationService notificationService = applicationContext.getBean(NotificationService.class);
        notificationService.saveNotification(notification);

        fcmService.sendMessageTo(review, PushType.valueOf(pushType));
    }
}

