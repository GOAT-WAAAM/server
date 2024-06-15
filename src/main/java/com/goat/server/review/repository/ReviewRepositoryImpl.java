package com.goat.server.review.repository;

import static com.goat.server.directory.application.type.SortType.REVIEW_SORT_MAP;
import static com.goat.server.review.domain.QReview.review;

import com.goat.server.directory.application.type.SortType;
import com.goat.server.review.domain.Review;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<Review> findByDirectoryId(Long directoryId, List<SortType> sort) {
        return query
                .selectFrom(review)
                .where(review.directory.id.eq(directoryId))
                .orderBy(sortExpression(sort))
                .fetch();
    }

    // 기본 정렬 - 이름 오름차순
    private OrderSpecifier<?>[] sortExpression(List<SortType> sort) {
        if (sort == null || sort.isEmpty()) {
            return new OrderSpecifier[]{
                    review.title.asc()
            };
        }
        return sort.stream()
                .map(sortType -> REVIEW_SORT_MAP.getOrDefault(sortType, review -> review.title.asc()).apply(review))
                .toArray(OrderSpecifier[]::new);
    }
}
