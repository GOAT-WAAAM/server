package com.goat.server.notification.util;

import com.goat.server.mypage.application.UserService;
import com.goat.server.mypage.domain.User;
import com.goat.server.mypage.exception.UserNotFoundException;
import com.goat.server.mypage.repository.UserRepository;
import com.goat.server.notification.application.FcmService;
import com.goat.server.notification.application.NotificationService;
import com.goat.server.notification.domain.Notification;
import com.goat.server.notification.domain.type.PushType;
import com.goat.server.review.domain.Review;
import com.goat.server.review.exception.ReviewNotFoundException;
import com.goat.server.review.repository.ReviewRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.goat.server.mypage.exception.errorcode.MypageErrorCode.USER_NOT_FOUND;
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

        try {
            sendMessageToClient(applicationContext, reviewId, pushType);
        } catch (Exception e) {
            log.error("FCM 전송 실패", e);
        }
    }

    private static void sendMessageToClient(ApplicationContext applicationContext, Long reviewId, String pushType) throws IOException {

        ReviewRepository reviewRepository = applicationContext.getBean(ReviewRepository.class);
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException(REVIEW_NOT_FOUND));

        UserRepository userRepository = applicationContext.getBean(UserRepository.class);
        User user = userRepository.findById(review.getUser().getUserId()).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        String content = user.getNickname() + "님! 복습할 시간이에요" + review.getDirectory().getTitle() + "의" + review.getTitle() + "을 지금 복습해보세요!";

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

