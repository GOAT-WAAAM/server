package com.goat.server.subject.fixture;

import com.goat.server.subject.domain.Subject;
import java.util.List;
import org.springframework.test.util.ReflectionTestUtils;

public class SubjectFixture {

    public static final Subject SUBJECT1 = Subject.builder()
            .subjectName("subject1")
            .subjectColor("color1")
            .directoryList(List.of(DirectoryFixture.DIRECTORY1, DirectoryFixture.DIRECTORY2))
            .build();

    public static final Subject SUBJECT2 = Subject.builder()
            .subjectName("subject2")
            .subjectColor("color2")
            .directoryList(List.of(DirectoryFixture.DIRECTORY3))
            .build();

    static {
        ReflectionTestUtils.setField(SUBJECT1, "subjectId", 1L);
        ReflectionTestUtils.setField(SUBJECT2, "subjectId", 2L);
    }
}
