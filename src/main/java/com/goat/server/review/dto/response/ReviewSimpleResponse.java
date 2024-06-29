package com.goat.server.review.dto.response;

import com.goat.server.review.domain.Review;

public record ReviewSimpleResponse(
        Long reviewId,
        String imageTitle
) {
        public static ReviewSimpleResponse from(Review review) {
            return new ReviewSimpleResponse(review.getId(), review.getTitle());
        }
}
