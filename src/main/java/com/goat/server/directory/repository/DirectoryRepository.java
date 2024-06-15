package com.goat.server.directory.repository;

import com.goat.server.directory.domain.Directory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectoryRepository extends JpaRepository<Directory, Long> {

    List<Directory> findAllByUserUserIdAndParentDirectoryIsNull(Long userId);

    List<Directory> findAllByParentDirectoryId(Long parentDirectoryId);

    @Query("SELECT d FROM Directory d WHERE d.user.userId = :userId AND d.title = 'trash_directory'")
    Optional<Directory> findTrashDirectoryByUser(Long userId);
}
