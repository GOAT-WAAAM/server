package com.goat.server.mypage.repository;

import com.goat.server.mypage.domain.User;
import com.goat.server.mypage.dto.JwtUserDetailProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySocialId(String string);

    @Query("SELECT u.userId as userId, u.role as role FROM User u WHERE u.userId = :userId")
    Optional<JwtUserDetailProjection> findJwtUserDetailsById(Long userId);
}
