package com.goat.server.review.dto.response;

public record StarReviewResponse(
        String star
) {
    public static StarReviewResponse from(String star) {
        return new StarReviewResponse(star);
    }
}
