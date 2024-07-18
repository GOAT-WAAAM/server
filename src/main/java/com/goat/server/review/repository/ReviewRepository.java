package com.goat.server.review.repository;

import com.goat.server.mypage.domain.User;
import com.goat.server.review.domain.Review;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Review> findByIdAndUser_UserId(Long reviewId, Long userId);

    @Query("SELECT r " +
            "FROM Review r JOIN FETCH StarReview sr ON r.id = sr.review.id " +
            "WHERE sr.user.userId = :userId")
    Page<Review> findAllStarReviewByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.user.userId = :userId AND r.imageInfo.imageUrl IS NOT NULL")
    Page<Review> findAllReviewImageByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT COALESCE(SUM(r.reviewCnt), 0) FROM Review r WHERE r.user.userId = :userId")
    Long sumReviewCntByUser(Long userId);

    List<Review> findAll();

    @Query("SELECT r FROM Review r " +
            "WHERE r.user.userId = :userId " +
            "AND r.directory.title != 'trash_directory' " +
            "AND r.reviewEndDate >= CURRENT_DATE")
    List<Review> findActiveReviewsByUserId(@Param("userId") Long userId);

    List<Review> findAllByUser(User user);
}
