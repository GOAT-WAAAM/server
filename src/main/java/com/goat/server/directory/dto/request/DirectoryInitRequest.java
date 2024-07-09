package com.goat.server.directory.dto.request;

import com.goat.server.directory.domain.Directory;
import com.goat.server.directory.domain.type.DirectoryColor;
import com.goat.server.directory.domain.type.DirectoryIcon;
import com.goat.server.directory.util.EnumValid;
import com.goat.server.mypage.domain.User;

public record DirectoryInitRequest(
        String directoryName,
        Long parentDirectoryId,
        @EnumValid(enumClass = DirectoryColor.class) String directoryColor,
        @EnumValid(enumClass = DirectoryIcon.class) String directoryIcon,
        String directoryDescription
) {
    public Directory toEntity(User user, Directory parentDirectory) {
        return Directory.builder()
                .user(user)
                .parentDirectory(parentDirectory)
                .depth(parentDirectory == null ? 1 : parentDirectory.getDepth() + 1)
                .title(directoryName)
                .directoryColor(DirectoryColor.valueOf(directoryColor))
                .directoryIcon(DirectoryIcon.valueOf(directoryIcon))
                .build();
    }
}