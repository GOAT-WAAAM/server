package com.goat.server.notification.util;

import com.goat.server.notification.application.FcmService;
import com.goat.server.review.domain.Review;
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
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        if (fcmService == null) {
            ApplicationContext appCtx = (ApplicationContext) jobExecutionContext.getJobDetail().getJobDataMap().get("appContext");
            fcmService = appCtx.getBean(FcmService.class);
        }

        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        ApplicationContext applicationContext = (ApplicationContext) dataMap.get("appContext");

        Long reviewId = dataMap.getLong("reviewId");

        ReviewRepository reviewRepository = applicationContext.getBean(ReviewRepository.class);
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new JobExecutionException("Review not found"));

        try {
            fcmService.sendMessageTo(review);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

