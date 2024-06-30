package com.goat.server.mypage.repository;

import com.goat.server.mypage.domain.Major;
import com.goat.server.mypage.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {
}
