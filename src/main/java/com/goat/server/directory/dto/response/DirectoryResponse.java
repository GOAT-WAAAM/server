package com.goat.server.directory.dto.response;

import com.goat.server.directory.domain.Directory;
import lombok.Builder;

@Builder
public record DirectoryResponse(
        Long directoryId,
        String directoryName,
        String directoryColor
) {

    public static DirectoryResponse from(Directory directory) {
        return DirectoryResponse.builder()
                .directoryId(directory.getDirectoryId())
                .directoryName(directory.getDirectoryName())
                .directoryColor(directory.getDirectoryColor())
                .build();
    }
}
