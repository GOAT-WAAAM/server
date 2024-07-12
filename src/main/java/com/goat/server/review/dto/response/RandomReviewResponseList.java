package com.goat.server.review.dto.response;

import java.util.List;

public record RandomReviewResponseList(
        List<RandomReviewResponse> randomReviewResponses
) {
    public static RandomReviewResponseList from(List<RandomReviewResponse> randomReviewResponses) {
        return new RandomReviewResponseList(randomReviewResponses);
    }
}