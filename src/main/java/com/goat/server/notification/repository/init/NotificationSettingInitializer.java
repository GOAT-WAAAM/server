package com.goat.server.notification.repository.init;

import com.goat.server.global.util.LocalDummyDataInit;
import com.goat.server.notification.domain.NotificationSetting;
import com.goat.server.notification.repository.NotificationSettingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

import static com.goat.server.mypage.repository.init.UserInitializer.DUMMY_MYPAGEUSER;

@Slf4j
@RequiredArgsConstructor
@LocalDummyDataInit
@Order(5)
public class NotificationSettingInitializer implements ApplicationRunner {

    private final NotificationSettingRepository notiSettingRepository;

    public static final NotificationSetting DUMMY_SETTING = NotificationSetting.builder()
            .user(DUMMY_MYPAGEUSER)
            .isCommentNoti(true)
            .isPostNoti(true)
            .isReviewNoti(true)
            .build();


    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (notiSettingRepository.count() > 0) {
            log.info("[Notification Setting]더미 데이터 존재");
        } else {
            List<NotificationSetting> settingList = new ArrayList<>();

            settingList.add(DUMMY_SETTING);

            notiSettingRepository.saveAll(settingList);
        }

    }
}
