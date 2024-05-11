package com.goat.server.subject.dto.response;

import com.goat.server.subject.domain.Directory;
import lombok.Builder;

@Builder
public record DirectoryResponse(
        Long directoryId,
        String directoryName
) {

    public static DirectoryResponse from(Directory directory) {
        return DirectoryResponse.builder()
                .directoryId(directory.getDirectoryId())
                .directoryName(directory.getDirectoryName())
                .build();
    }
}
