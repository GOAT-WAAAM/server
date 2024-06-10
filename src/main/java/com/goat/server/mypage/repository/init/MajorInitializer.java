package com.goat.server.mypage.repository.init;

import com.goat.server.global.util.LocalDummyDataInit;
import com.goat.server.mypage.domain.Major;
import com.goat.server.mypage.domain.User;
import com.goat.server.mypage.exception.UserNotFoundException;
import com.goat.server.mypage.exception.errorcode.MypageErrorCode;
import com.goat.server.mypage.repository.MajorRepository;
import com.goat.server.mypage.repository.UserRepository;
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
@Order(3)
public class MajorInitializer implements ApplicationRunner {

    private final MajorRepository majorRepository;
    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) {

        if (majorRepository.count() > 0) {
            log.info("[Major]더미 데이터 존재");
        } else {
            User admin = userRepository.findByEmail("adminEmail")
                    .orElseThrow(() -> new UserNotFoundException(MypageErrorCode.USER_NOT_FOUND));
            User user = userRepository.findByEmail("userEmail")
                    .orElseThrow(() -> new UserNotFoundException(MypageErrorCode.USER_NOT_FOUND));
            User guest = userRepository.findByEmail("guestEmail")
                    .orElseThrow(() -> new UserNotFoundException(MypageErrorCode.USER_NOT_FOUND));

            List<Major> majorList = new ArrayList<>();

            Major DUMMY_ADMIN_MAJOR1 = Major.builder()
                    .majorName("dummyMajor1")
                    .user(user)
                    .build();

            Major DUMMY_USER_MAJOR1 = Major.builder()
                    .majorName("dummyMajor1")
                    .user(user)
                    .build();

            Major DUMMY_USER_MAJOR2 = Major.builder()
                    .majorName("dummyMajor2")
                    .user(user)
                    .build();

            Major DUMMY_USER_MAJOR3 = Major.builder()
                    .majorName("dummyMajor3")
                    .user(user)
                    .build();

            Major DUMMY_GUEST_MAJOR1 = Major.builder()
                    .majorName("dummyMajor1")
                    .user(user)
                    .build();

            majorList.add(DUMMY_ADMIN_MAJOR1);
            majorList.add(DUMMY_USER_MAJOR1);
            majorList.add(DUMMY_USER_MAJOR2);
            majorList.add(DUMMY_USER_MAJOR3);
            majorList.add(DUMMY_GUEST_MAJOR1);

            majorRepository.saveAll(majorList);
        }
    }
}
