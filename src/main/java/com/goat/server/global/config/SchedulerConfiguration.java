package com.goat.server.global.config;

import com.goat.server.notification.domain.type.PushType;
import com.goat.server.notification.util.FcmJob;
import com.goat.server.notification.util.FcmJobListener;
import com.goat.server.review.domain.Review;
import com.goat.server.review.domain.ReviewDate;
import com.goat.server.review.repository.ReviewDateRepository;
import com.goat.server.review.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Slf4j
@Configuration
public class SchedulerConfiguration implements WebMvcConfigurer {

    private Scheduler scheduler;
    private final ApplicationContext applicationContext;

    private static String APPLICATION_NAME = "appContext";

    public SchedulerConfiguration(Scheduler sch, ApplicationContext applicationContext) {
        this.scheduler = sch;
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    private void configScheduler() throws SchedulerException {
        // 스케줄러 초기화
        scheduler.start();
        FcmJobListener fcmJobListener = new FcmJobListener();
        scheduler.getListenerManager().addJobListener(fcmJobListener);

        // 모든 리뷰 스케줄 설정
        ReviewRepository reviewRepository = applicationContext.getBean(ReviewRepository.class);
        List<Review> reviews = reviewRepository.findAll();

        for (Review review : reviews) {
            scheduleReviewNotifications(review);
        }
    }

    public void scheduleReviewNotifications(Review review) throws SchedulerException {
        ReviewDateRepository reviewDateRepository = applicationContext.getBean(ReviewDateRepository.class);
        List<ReviewDate> reviewDates = reviewDateRepository.findByReviewId(review.getId());

        for (ReviewDate reviewDate : reviewDates) {
            scheduleCustomReviewNotification(review, reviewDate);
        }
    }

    public void scheduleCustomReviewNotification(Review review, ReviewDate reviewDate) throws SchedulerException {
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(APPLICATION_NAME, applicationContext);
        dataMap.put("reviewId", review.getId());
        dataMap.put("pushType", PushType.CUSTOM_REVIEW.name());

        JobDetail job = JobBuilder
                .newJob(FcmJob.class)
                .withIdentity("fcmSendJob" + review.getId() + reviewDate.getDate().toString(), "fcmGroup")
                .setJobData(dataMap)
                .build();

        ZoneId zoneId = ZoneId.systemDefault();
        Instant startInstant = review.getReviewStartDate().atStartOfDay(zoneId).toInstant();
        Instant endInstant = review.getReviewEndDate().atTime(LocalTime.MAX).atZone(zoneId).toInstant();

        if (startInstant.isAfter(endInstant)) {
            throw new SchedulerException("시작 날짜는 종료 날짜 이전이어야 합니다.");
        }

        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("fcmSendTrigger" + review.getId() + reviewDate.getDate().toString(), "fcmGroup")
                .withSchedule(
                        CronScheduleBuilder
                                .weeklyOnDayAndHourAndMinute(getDayOfWeek(reviewDate.getDate().name()),
                                        review.getRemindTime().getHour(),
                                        review.getRemindTime().getMinute())
                                .withMisfireHandlingInstructionDoNothing())
                .startAt(Date.from(startInstant))
                .endAt(Date.from(endInstant))
                .build();

        try {
            scheduler.scheduleJob(job, trigger);
            log.info("[scheduleReviewNotification] Review Notification Scheduled: " + review.getId() + " " + reviewDate.getDate().name() + " " + review.getRemindTime().toString());
        } catch (SchedulerException e) {
            log.error("[scheduleReviewNotification] Review Notification Scheduling Failed: " + e.getMessage());
        }
    }

    public void scheduleAutoReviewNotification(Review review) throws SchedulerException {
        ZoneId zoneId = ZoneId.systemDefault();
        Instant registrationTime = Instant.now();

//        scheduleNotification(review, registrationTime.plus(1, ChronoUnit.HOURS), PushType.AUTO_REVIEW_ONE_HOUR);
//        scheduleNotification(review, registrationTime.plus(24, ChronoUnit.HOURS), PushType.AUTO_REVIEW_ONE_DAY);
//        scheduleNotification(review, registrationTime.plus(6, ChronoUnit.DAYS), PushType.AUTO_REVIEW_ONE_WEEK);
//        scheduleNotification(review, registrationTime.plus(30, ChronoUnit.DAYS), PushType.AUTO_REVIEW_ONE_MONTH);

        scheduleNotification(review, registrationTime.plus(10, ChronoUnit.SECONDS), PushType.AUTO_REVIEW_ONE_HOUR);
        scheduleNotification(review, registrationTime.plus(20, ChronoUnit.SECONDS), PushType.AUTO_REVIEW_ONE_DAY);
        scheduleNotification(review, registrationTime.plus(30, ChronoUnit.SECONDS), PushType.AUTO_REVIEW_ONE_WEEK);
        scheduleNotification(review, registrationTime.plus(40, ChronoUnit.SECONDS), PushType.AUTO_REVIEW_ONE_MONTH);
    }

    private void scheduleNotification(Review review, Instant notificationTime, PushType pushType) throws SchedulerException {
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(APPLICATION_NAME, applicationContext);
        dataMap.put("reviewId", review.getId());
        dataMap.put("pushType", pushType.name());

        String notificationIdentity = "fcmSendJob" + review.getId() + "-" + notificationTime.toString();

        JobDetail job = JobBuilder.newJob(FcmJob.class)
                .withIdentity(notificationIdentity, "fcmGroup")
                .setJobData(dataMap)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("fcmSendTrigger" + notificationIdentity, "fcmGroup")
                .startAt(Date.from(notificationTime))
                .build();

        try {
            scheduler.scheduleJob(job, trigger);
            log.info("[scheduleNotification] Notification Scheduled: " + notificationIdentity);
        } catch (SchedulerException e) {
            log.error("[scheduleNotification] Notification Scheduling Failed: " + e.getMessage());
        }
    }



    private int getDayOfWeek(String dayOfWeek) {
        switch (dayOfWeek.toUpperCase()) {
            case "MON":
                return DateBuilder.MONDAY;
            case "TUE":
                return DateBuilder.TUESDAY;
            case "WED":
                return DateBuilder.WEDNESDAY;
            case "THU":
                return DateBuilder.THURSDAY;
            case "FRI":
                return DateBuilder.FRIDAY;
            case "SAT":
                return DateBuilder.SATURDAY;
            case "SUN":
                return DateBuilder.SUNDAY;
            default:
                throw new IllegalArgumentException("Invalid day of week: " + dayOfWeek);
        }
    }

}
