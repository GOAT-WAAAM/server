package com.goat.server.mypage.repository;

import com.goat.server.mypage.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findBySocialId(String string);

    Optional<User> findByEmail(String email);
}
