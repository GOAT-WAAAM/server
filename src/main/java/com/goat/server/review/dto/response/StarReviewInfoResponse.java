package com.goat.server.review.dto.response;

import com.goat.server.review.domain.Review;
import lombok.Builder;

@Builder
public record StarReviewInfoResponse(
        Long reviewId,
        Boolean isStar,
        String imageUrl,
        String title,
        String directoryName
) {
    public static StarReviewInfoResponse from(Review review){
        return StarReviewInfoResponse.builder()
                .reviewId(review.getId())
                .isStar(review.getIsStar())
                .imageUrl(review.getImageUrl())
                .title(review.getTitle())
                .directoryName(review.getDirectory().getTitle())
                .build();
    }
}
