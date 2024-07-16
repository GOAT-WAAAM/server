package com.goat.server.review.dto.response;

import java.util.List;

public record RandomReviewsResponseList(
        List<RandomReviewsResponse> randomReviewResponses
) {
    public static RandomReviewsResponseList from(List<RandomReviewsResponse> randomReviewResponses) {
        return new RandomReviewsResponseList(randomReviewResponses);
    }
}
