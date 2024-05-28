package com.goat.server.directory.repository;

import com.goat.server.directory.domain.Directory;
import com.goat.server.mypage.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectoryRepository extends JpaRepository<Directory, Long> {

    List<Directory> findAllByUserAndParentDirectoryIsNull(User user);

    @Query("SELECT d FROM Directory d WHERE d.user = :user AND d.directoryName = 'trash'")
    Optional<Directory> findTrashDirectoryByUser(User user);
}
