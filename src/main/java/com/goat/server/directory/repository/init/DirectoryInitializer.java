package com.goat.server.directory.repository.init;

import com.goat.server.global.util.LocalDummyDataInit;
import com.goat.server.directory.domain.Directory;
import com.goat.server.directory.repository.DirectoryRepository;
import com.goat.server.mypage.repository.init.UserInitializer;
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

    public static final Directory DUMMY_TRASH_DIRECTORY1 = Directory.builder()
            .directoryName("trash")
            .directoryColor("#FF00FF")
            .parentDirectory(null)
            .user(UserInitializer.DUMMY_USER)
            .build();

    public static final Directory DUMMY_TRASH_DIRECTORY2 = Directory.builder()
            .directoryName("trash")
            .directoryColor("#FF00FF")
            .parentDirectory(null)
            .user(UserInitializer.DUMMY_GUEST)
            .build();

    public static final Directory DUMMY_TRASH_DIRECTORY3 = Directory.builder()
            .directoryName("trash")
            .directoryColor("#FF00FF")
            .parentDirectory(null)
            .user(UserInitializer.DUMMY_ADMIN)
            .build();

    public static final Directory DUMMY_PARENT_DIRECTORY1 = Directory.builder()
            .directoryName("dummyDirectory1")
            .directoryColor("#FF00FF")
            .parentDirectory(null)
            .user(UserInitializer.DUMMY_USER)
            .build();

    public static final Directory DUMMY_PARENT_DIRECTORY2 = Directory.builder()
            .directoryName("dummyDirectory2")
            .directoryColor("#FF00FF")
            .parentDirectory(null)
            .user(UserInitializer.DUMMY_USER)
            .build();

    public static final Directory DUMMY_PARENT_DIRECTORY3 = Directory.builder()
            .directoryName("dummyDirectory3")
            .directoryColor("#FF000F")
            .parentDirectory(null)
            .user(UserInitializer.DUMMY_ADMIN)
            .build();

    public static final Directory DUMMY_CHILD_DIRECTORY1 = Directory.builder()
            .directoryName("dummyDirectory4")
            .directoryColor("#FF00FF")
            .parentDirectory(DUMMY_PARENT_DIRECTORY1)
            .user(UserInitializer.DUMMY_USER)
            .build();

    public static final Directory DUMMY_CHILD_DIRECTORY2 = Directory.builder()
            .directoryName("dummyDirectory5")
            .directoryColor("#FF00FF")
            .parentDirectory(DUMMY_PARENT_DIRECTORY1)
            .user(UserInitializer.DUMMY_USER)
            .build();

    @Override
    public void run(ApplicationArguments args) {

        if (directoryRepository.count() > 0) {
            log.info("[Directory]더미 데이터 존재");
        } else {
            List<Directory> directoryList = new ArrayList<>();

            directoryList.add(DUMMY_TRASH_DIRECTORY1);
            directoryList.add(DUMMY_TRASH_DIRECTORY2);
            directoryList.add(DUMMY_TRASH_DIRECTORY3);
            directoryList.add(DUMMY_PARENT_DIRECTORY1);
            directoryList.add(DUMMY_PARENT_DIRECTORY2);
            directoryList.add(DUMMY_PARENT_DIRECTORY3);
            directoryList.add(DUMMY_CHILD_DIRECTORY1);
            directoryList.add(DUMMY_CHILD_DIRECTORY2);

            directoryRepository.saveAll(directoryList);
        }
    }
}
