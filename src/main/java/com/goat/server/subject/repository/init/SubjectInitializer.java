package com.goat.server.subject.repository.init;

import static com.goat.server.mypage.repository.init.UserInitializer.DUMMY_ADMIN;
import static com.goat.server.mypage.repository.init.UserInitializer.DUMMY_USER;

import com.goat.server.global.util.LocalDummyDataInit;
import com.goat.server.subject.domain.Subject;
import com.goat.server.subject.repository.SubjectRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

@Slf4j
@RequiredArgsConstructor
@LocalDummyDataInit
@Order(2)
public class SubjectInitializer implements ApplicationRunner {

    private final SubjectRepository subjectRepository;

    public static final Subject DUMMY_SUBJECT1 = Subject.builder()
            .subjectName("dummySubject1")
            .subjectColor("dummyColor1")
            .user(DUMMY_USER)
            .build();

    public static final Subject DUMMY_SUBJECT2 = Subject.builder()
            .subjectName("dummySubject2")
            .subjectColor("dummyColor2")
            .user(DUMMY_USER)
            .build();

    public static final Subject DUMMY_SUBJECT3 = Subject.builder()
            .subjectName("dummySubject3")
            .subjectColor("dummyColor3")
            .user(DUMMY_ADMIN)
            .build();

    @Override
    public void run(ApplicationArguments args) {

        if (subjectRepository.count() > 0) {
            log.info("[Subject]더미 데이터 존재");
        } else {
            List<Subject> subjectList = new ArrayList<>();

            subjectList.add(DUMMY_SUBJECT1);
            subjectList.add(DUMMY_SUBJECT2);
            subjectList.add(DUMMY_SUBJECT3);

            subjectRepository.saveAll(subjectList);
        }
    }
}
