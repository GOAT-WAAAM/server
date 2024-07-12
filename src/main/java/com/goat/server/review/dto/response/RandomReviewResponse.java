package com.goat.server.review.dto.response;

import com.goat.server.review.domain.Review;
import lombok.Builder;

@Builder
public record RandomReviewResponse(
        Long reviewId,
        String imageUrl,
        String content
) {
    public static RandomReviewResponse from(Review review) {
        return RandomReviewResponse.builder()
                .reviewId(review.getId())
                .imageUrl(review.getImageUrl())
                .content(review.getContent())
                .build();
    }
}