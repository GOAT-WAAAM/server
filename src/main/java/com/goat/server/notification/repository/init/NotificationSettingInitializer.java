package com.goat.server.notification.repository.init;

import com.goat.server.global.util.LocalDummyDataInit;
import com.goat.server.mypage.domain.User;
import com.goat.server.mypage.exception.UserNotFoundException;
import com.goat.server.mypage.exception.errorcode.MypageErrorCode;
import com.goat.server.mypage.repository.UserRepository;
import com.goat.server.notification.domain.NotificationSetting;
import com.goat.server.notification.repository.NotificationSettingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@LocalDummyDataInit
@Order(4)
public class NotificationSettingInitializer implements ApplicationRunner {

    private final NotificationSettingRepository notiSettingRepository;
    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) {

        if (notiSettingRepository.count() > 0) {
            log.info("[Notification Setting]더미 데이터 존재");
        } else {
            User admin = userRepository.findByEmail("adminEmail")
                    .orElseThrow(() -> new UserNotFoundException(MypageErrorCode.USER_NOT_FOUND));
            User user = userRepository.findByEmail("userEmail")
                    .orElseThrow(() -> new UserNotFoundException(MypageErrorCode.USER_NOT_FOUND));
            User guest = userRepository.findByEmail("guestEmail")
                    .orElseThrow(() -> new UserNotFoundException(MypageErrorCode.USER_NOT_FOUND));

            List<NotificationSetting> settingList = new ArrayList<>();
            NotificationSetting DUMMY_NOTISETTING1 = NotificationSetting.builder()
                    .user(admin)
                    .isCommentNoti(false)
                    .isPostNoti(false)
                    .isReviewNoti(true)
                    .build();

            NotificationSetting DUMMY_NOTISETTING2 = NotificationSetting.builder()
                    .user(user)
                    .isCommentNoti(true)
                    .isPostNoti(true)
                    .isReviewNoti(true)
                    .build();

            NotificationSetting DUMMY_NOTISETTING3 = NotificationSetting.builder()
                    .user(guest)
                    .isCommentNoti(false)
                    .isPostNoti(false)
                    .isReviewNoti(false)
                    .build();

            settingList.add(DUMMY_NOTISETTING1);
            settingList.add(DUMMY_NOTISETTING2);
            settingList.add(DUMMY_NOTISETTING3);

            notiSettingRepository.saveAll(settingList);
        }

    }
}
