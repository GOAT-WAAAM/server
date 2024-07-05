package com.goat.server.review.repository;

import com.goat.server.directory.application.type.SortType;
import com.goat.server.review.dto.response.ReviewSimpleResponse;
import java.util.List;

public interface ReviewRepositoryCustom {

    List<ReviewSimpleResponse> findAllReviewSimpleResponseBySortAndSearch(
            Long userId, Long parentDirectoryId, List<SortType> sort, String search);
}
