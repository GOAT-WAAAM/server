package com.goat.server.review.repository;

import com.goat.server.directory.application.type.SortType;
import com.goat.server.review.domain.Review;
import java.util.List;

public interface ReviewRepositoryCustom {

    List<Review> findAllByDirectoryId(Long directoryId, List<SortType> sort);

    List<Review> findAllBySearch(Long userId, String search, List<SortType> sort);
}
