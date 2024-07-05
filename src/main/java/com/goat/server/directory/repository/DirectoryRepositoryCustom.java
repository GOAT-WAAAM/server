package com.goat.server.directory.repository;

import com.goat.server.directory.application.type.SortType;
import com.goat.server.directory.dto.response.DirectoryResponse;
import java.util.List;

public interface DirectoryRepositoryCustom {
    List<DirectoryResponse> findAllDirectoryResponseBySortAndSearch(
            Long userId, Long parentDirectoryId, List<SortType> sort, String search);
}
