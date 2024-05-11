package com.goat.server.subject.dto.response;

import java.util.List;

public record SubjectResponseList(
        List<SubjectResponse> subjectResponseList
) {
    public static SubjectResponseList from(List<SubjectResponse> subjectResponseList) {
        return new SubjectResponseList(subjectResponseList);
    }
}
