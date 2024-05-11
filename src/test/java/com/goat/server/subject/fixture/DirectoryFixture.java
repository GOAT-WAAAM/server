package com.goat.server.subject.fixture;

import static com.goat.server.subject.fixture.SubjectFixture.SUBJECT1;
import static com.goat.server.subject.fixture.SubjectFixture.SUBJECT2;

import com.goat.server.subject.domain.Directory;
import org.springframework.test.util.ReflectionTestUtils;

public class DirectoryFixture {

    public static final Directory DIRECTORY1 = Directory.builder()
            .directoryName("directory1")
            .subject(SUBJECT1)
            .build();

    public static final Directory DIRECTORY2 = Directory.builder()
            .directoryName("directory2")
            .subject(SUBJECT1)
            .build();

    public static final Directory DIRECTORY3 = Directory.builder()
            .directoryName("directory3")
            .subject(SUBJECT2)
            .build();

    static {
        ReflectionTestUtils.setField(DIRECTORY1, "directoryId", 1L);
        ReflectionTestUtils.setField(DIRECTORY2, "directoryId", 2L);
        ReflectionTestUtils.setField(DIRECTORY3, "directoryId", 3L);
    }
}
