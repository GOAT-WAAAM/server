package com.goat.server.review.repository;

import com.goat.server.review.domain.ReviewDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewDateRepository extends JpaRepository<ReviewDate, Long> {
}
