package com.goat.server.review.repository;

import com.goat.server.review.domain.UnViewedReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnViewedReviewRepository extends JpaRepository<UnViewedReview, Long> {

    void deleteByReviewId(Long reviewId);

    @Query("SELECT u FROM UnViewedReview u WHERE u.user.userId = :userId ORDER BY u.id ASC LIMIT 1")
    Optional<UnViewedReview> findFirstByUserId(@Param("userId") Long userId);
}
