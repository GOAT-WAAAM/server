package com.goat.server.directory.dto.response;

import com.goat.server.directory.domain.Directory;
import com.goat.server.directory.domain.type.DirectoryColor;
import com.goat.server.directory.domain.type.DirectoryIcon;
import lombok.Builder;

@Builder
public record DirectoryResponse(
        Long directoryId,
        String directoryName,
        DirectoryColor directoryColor,
        DirectoryIcon directoryIcon
) {

    public static DirectoryResponse from(Directory directory) {
        return DirectoryResponse.builder()
                .directoryId(directory.getId())
                .directoryName(directory.getTitle())
                .directoryColor(directory.getDirectoryColor())
                .directoryIcon(directory.getDirectoryIcon())
                .build();
    }
}
