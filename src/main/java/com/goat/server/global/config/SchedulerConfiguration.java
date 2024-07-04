package com.goat.server.global.config;

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
            scheduleReviewNotification(review, reviewDate);
        }
    }

    public void scheduleReviewNotification(Review review, ReviewDate reviewDate) throws SchedulerException {
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(APPLICATION_NAME, applicationContext);
        dataMap.put("reviewId", review.getId());

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
