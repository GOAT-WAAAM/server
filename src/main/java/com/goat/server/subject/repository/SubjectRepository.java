package com.goat.server.subject.repository;

import com.goat.server.mypage.domain.User;
import com.goat.server.subject.domain.Subject;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query("select s from Subject s join fetch s.directoryList where s.user = :user")
    List<Subject> findSubjectsAndDirectories(User user);
}
