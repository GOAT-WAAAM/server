package com.goat.server.review.dto.response;

import com.goat.server.review.domain.Review;
import lombok.Builder;

@Builder
public record StarReviewInfoResponse(
        Long reviewId,
        String imageUrl,
        String title
) {
    public static StarReviewInfoResponse of(Review review){
        return StarReviewInfoResponse.builder()
                .reviewId(review.getId())
                .imageUrl(review.getImageInfo().getImageUrl())
                .title(review.getTitle())
                .build();
    }
}
