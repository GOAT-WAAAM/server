package com.goat.server.review.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record StarReviewResponseList(
        List<StarReviewInfoResponse> starReviewInfoResponseList
) {
    public static StarReviewResponseList from (List<StarReviewInfoResponse> starReviewInfoResponses){
        return new StarReviewResponseList(starReviewInfoResponses);
    }
}
