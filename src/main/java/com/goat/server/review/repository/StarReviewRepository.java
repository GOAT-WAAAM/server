package com.goat.server.review.repository;

import com.goat.server.mypage.domain.User;
import com.goat.server.review.domain.Review;
import com.goat.server.review.domain.StarReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StarReviewRepository extends JpaRepository<StarReview, Long> {
    Optional<StarReview> findByUserAndReview(User user, Review review);
}
