package com.goat.server.directory.dto.request;

public record DirectoryMoveRequest(
        Long moveDirectoryId,
        Long targetDirectoryId
) {
}
