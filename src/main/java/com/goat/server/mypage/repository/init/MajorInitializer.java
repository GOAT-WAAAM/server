package com.goat.server.mypage.repository.init;

import com.goat.server.global.util.LocalDummyDataInit;
import com.goat.server.mypage.domain.Major;
import com.goat.server.mypage.domain.User;
import com.goat.server.mypage.repository.MajorRepository;
import com.goat.server.subject.domain.Subject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

import static com.goat.server.mypage.repository.init.UserInitializer.DUMMY_GUEST;
import static com.goat.server.mypage.repository.init.UserInitializer.DUMMY_MYPAGEUSER;


@Slf4j
@RequiredArgsConstructor
@LocalDummyDataInit
@Order(4)
public class MajorInitializer implements ApplicationRunner {

    private final MajorRepository majorRepository;

    public static final Major DUMMY_MAJOR1 = Major.builder()
            .majorName("dummyMajor1")
            .user(DUMMY_GUEST)
            .build();

    public static final Major DUMMY_MAJOR2 = Major.builder()
            .majorName("dummyMajor2")
            .user(DUMMY_MYPAGEUSER)
            .build();

    public static final Major DUMMY_MAJOR3 = Major.builder()
            .majorName("dummyMajor3")
            .user(DUMMY_MYPAGEUSER)
            .build();

    @Override
    public void run(ApplicationArguments args) {

        if (majorRepository.count() > 0) {
            log.info("[Major]더미 데이터 존재");
        } else {
            List<Major> majorList = new ArrayList<>();

            majorList.add(DUMMY_MAJOR1);
            majorList.add(DUMMY_MAJOR2);
            majorList.add(DUMMY_MAJOR3);

            majorRepository.saveAll(majorList);
        }
    }
}
