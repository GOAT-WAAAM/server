package com.goat.server.review.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ReviewHomeResponseList(
        List<ReviewHomeResponse> homeResponseList
) {
    public static ReviewHomeResponseList from(List<ReviewHomeResponse> homeResponses) {
        return new ReviewHomeResponseList(homeResponses);
    }
}