package com.goat.server.subject.dto.response;

import com.goat.server.subject.domain.Subject;
import lombok.Builder;

@Builder
public record SubjectResponse(
        Long subjectId,
        String subjectName,
        String subjectColor,
        DirectoryResponseList directoryResponseList
) {
    public static SubjectResponse from(Subject subject) {
        return SubjectResponse.builder()
                .subjectId(subject.getSubjectId())
                .subjectName(subject.getSubjectName())
                .subjectColor(subject.getSubjectColor())
                .directoryResponseList(DirectoryResponseList.from(subject.getDirectoryList()))
                .build();
    }
}
