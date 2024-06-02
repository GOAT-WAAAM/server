package com.goat.server.directory.dto.response;

import com.goat.server.review.dto.response.ReviewSimpleResponse;
import java.util.List;

public record DirectoryTotalShowResponse(
        List<DirectoryResponse> directoryResponseList,
        List<ReviewSimpleResponse> reviewSimpleResponseList
) {
    public static DirectoryTotalShowResponse of(List<DirectoryResponse> directoryResponseList,
                                                List<ReviewSimpleResponse> reviewSimpleResponseList) {
        return new DirectoryTotalShowResponse(directoryResponseList, reviewSimpleResponseList);
    }
}
