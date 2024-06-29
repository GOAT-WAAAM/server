package com.goat.server.mypage.repository;

import com.goat.server.mypage.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u left join fetch u.majorList where u.userId = :userId")
    User findUserWithMajors(@Param("userId") Long userId);

    Optional<User> findBySocialId(String string);

    Optional<User> findByEmail(String email);
}
