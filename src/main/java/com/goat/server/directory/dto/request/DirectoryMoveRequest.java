package com.goat.server.directory.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DirectoryMoveRequest(
        @Positive Long moveDirectoryId,
        @NotNull Long targetDirectoryId
) {
}
