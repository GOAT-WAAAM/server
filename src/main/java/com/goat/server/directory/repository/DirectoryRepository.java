package com.goat.server.directory.repository;

import com.goat.server.directory.domain.Directory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectoryRepository extends JpaRepository<Directory, Long> {

    @Query("SELECT d FROM Directory d WHERE d.user.userId = :userId AND d.parentDirectory IS NULL")
    List<Directory> findAllByUserIdAndParentDirectoryIsNull(Long userId);

    List<Directory> findByParentDirectoryId(Long parentDirectoryId);

    @Query("SELECT d FROM Directory d WHERE d.user.userId = :userId AND d.directoryName = 'trash_directory'")
    Optional<Directory> findTrashDirectoryByUser(Long userId);
}
