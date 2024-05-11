package com.goat.server.subject.dto.response;

import com.goat.server.subject.domain.Directory;
import java.util.List;

public record DirectoryResponseList(
        List<DirectoryResponse> directoryResponseList
) {
    public static DirectoryResponseList from(List<Directory> directoryList) {
        return new DirectoryResponseList(directoryList.stream()
                .map(DirectoryResponse::from)
                .toList());
    }
}
