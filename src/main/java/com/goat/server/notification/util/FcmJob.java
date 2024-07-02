package com.goat.server.notification.util;

import com.goat.server.global.util.ApplicationContextProvider;
import com.goat.server.notification.application.FcmService;
import com.goat.server.notification.dto.FcmSendDeviceDto;
import com.goat.server.notification.dto.FcmSendDto;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

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

        List<FcmSendDeviceDto> selectFcmSendList = fcmService.selectFcmSendList();

        for (FcmSendDeviceDto fcmSendItem : selectFcmSendList) {

            FcmSendDto fcmSendDto = FcmSendDto.builder()
                    .token(fcmSendItem.deviceToken())
                    .title("푸시 메시지입니다!")
                    .body("계획된 시간이 되었어요!")
                    .build();
            try {
                fcmService.sendMessageTo(fcmSendDto);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

