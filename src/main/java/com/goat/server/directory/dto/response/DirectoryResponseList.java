package com.goat.server.directory.dto.response;

import java.util.List;

public record DirectoryResponseList(
        List<DirectoryResponse> directoryResponseList
) {
    public static DirectoryResponseList from(List<DirectoryResponse> directoryList) {
        return new DirectoryResponseList(directoryList);
    }
}
