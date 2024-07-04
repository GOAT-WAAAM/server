package com.goat.server.review.repository;

import com.goat.server.review.domain.ReviewDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewDateRepository extends JpaRepository<ReviewDate, Long> {
    List<ReviewDate> findByReviewId(Long id);
}
