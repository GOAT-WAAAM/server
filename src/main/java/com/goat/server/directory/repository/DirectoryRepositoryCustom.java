package com.goat.server.directory.repository;

import com.goat.server.directory.application.type.SortType;
import com.goat.server.directory.domain.Directory;
import java.util.List;

public interface DirectoryRepositoryCustom {
    List<Directory> findAllByParentDirectoryId(Long parentDirectoryId, List<SortType> sort);

    List<Directory> findAllByUserIdAndParentDirectoryIsNull(Long userId, List<SortType> sort);
}
