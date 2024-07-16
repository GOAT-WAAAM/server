package com.goat.server.review.dto.response;

import com.goat.server.review.domain.Review;
import com.goat.server.review.domain.UnViewedReview;
import lombok.Builder;

import java.util.Optional;

@Builder
public record RandomReviewsResponse(
        Long reviewId,
        String imageUrl,
        String content
) {
        public static RandomReviewsResponse from(Review review) {
                return RandomReviewsResponse.builder()
                .reviewId(review.getId())
                .imageUrl(review.getImageUrl())
                .content(review.getContent())
                .build();
        }

}
