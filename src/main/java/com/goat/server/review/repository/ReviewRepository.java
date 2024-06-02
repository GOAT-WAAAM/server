package com.goat.server.review.repository;

import com.goat.server.review.domain.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByDirectory_DirectoryId(Long directoryId);
}
