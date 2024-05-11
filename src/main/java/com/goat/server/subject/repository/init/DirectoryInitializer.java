package com.goat.server.subject.repository.init;

import static com.goat.server.subject.repository.init.SubjectInitializer.DUMMY_SUBJECT1;
import static com.goat.server.subject.repository.init.SubjectInitializer.DUMMY_SUBJECT2;
import static com.goat.server.subject.repository.init.SubjectInitializer.DUMMY_SUBJECT3;

import com.goat.server.global.util.LocalDummyDataInit;
import com.goat.server.subject.domain.Directory;
import com.goat.server.subject.repository.DirectoryRepository;
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
@Order(3)
public class DirectoryInitializer implements ApplicationRunner {

    private final DirectoryRepository directoryRepository;

    public static final Directory DUMMY_DIRECTORY1 = Directory.builder()
            .directoryName("dummyDirectory1")
            .subject(DUMMY_SUBJECT1)
            .build();

    public static final Directory DUMMY_DIRECTORY2 = Directory.builder()
            .directoryName("dummyDirectory2")
            .subject(DUMMY_SUBJECT1)
            .build();

    public static final Directory DUMMY_DIRECTORY3 = Directory.builder()
            .directoryName("dummyDirectory3")
            .subject(DUMMY_SUBJECT2)
            .build();

    public static final Directory DUMMY_DIRECTORY4 = Directory.builder()
            .directoryName("dummyDirectory4")
            .subject(DUMMY_SUBJECT2)
            .build();

    public static final Directory DUMMY_DIRECTORY5 = Directory.builder()
            .directoryName("dummyDirectory5")
            .subject(DUMMY_SUBJECT3)
            .build();

    @Override
    public void run(ApplicationArguments args) {

        if (directoryRepository.count() > 0) {
            log.info("[Directory]더미 데이터 존재");
        } else {
            List<Directory> directoryList = new ArrayList<>();

            directoryList.add(DUMMY_DIRECTORY1);
            directoryList.add(DUMMY_DIRECTORY2);
            directoryList.add(DUMMY_DIRECTORY3);
            directoryList.add(DUMMY_DIRECTORY4);
            directoryList.add(DUMMY_DIRECTORY5);

            directoryRepository.saveAll(directoryList);
        }
    }
}
