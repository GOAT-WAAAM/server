package com.goat.server.directory.dto.request;

import com.goat.server.directory.domain.Directory;
import com.goat.server.mypage.domain.User;

public record DirectoryInitRequest(
        String directoryName,
        Long parentDirectoryId,
        String directoryColor
) {
    public Directory toEntity(User user, Directory parentDirectory) {
        return Directory.builder()
                .user(user)
                .parentDirectory(parentDirectory)
                .depth(parentDirectory == null ? 1 : parentDirectory.getDepth() + 1)
                .title(directoryName)
                .directoryColor(directoryColor)
                .build();
    }
}