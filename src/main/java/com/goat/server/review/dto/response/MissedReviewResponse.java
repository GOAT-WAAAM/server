package com.goat.server.review.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record MissedReviewResponse(
        List<ReviewSimpleResponse> reviewSimpleResponses
) {
    public static MissedReviewResponse from(List<ReviewSimpleResponse> reviewSimpleResponseList) {
        return new MissedReviewResponse(reviewSimpleResponseList);
    }
}
