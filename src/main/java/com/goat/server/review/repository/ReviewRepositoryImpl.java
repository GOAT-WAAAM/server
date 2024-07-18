package com.goat.server.review.repository;

import static com.goat.server.directory.application.type.SortType.REVIEW_SORT_MAP;
import static com.goat.server.review.domain.QReview.review;

import com.goat.server.directory.application.type.SortType;
import com.goat.server.review.dto.response.ReviewSimpleResponse;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<ReviewSimpleResponse> findAllReviewSimpleResponseBySortAndSearch(
            Long userId, Long parentDirectoryId, List<SortType> sort, String search) {
        return query
                .select(Projections.constructor(ReviewSimpleResponse.class,
                        review.id,
                        review.title,
                        review.imageInfo.imageUrl,
                        review.reviewCnt))
                .from(review)
                .where(review.user.userId.eq(userId), searchExpression(parentDirectoryId, search))
                .orderBy(sortExpression(sort))
                .fetch();
    }

    //search가 null이면 parentDirectoryId로 검색, 아니면 search로 검색 - search 존재 -> 전체 검색
    private BooleanExpression searchExpression(Long parentDirectoryId, String search) {
        if (StringUtils.hasLength(search)) {
            return review.directory.id.eq(parentDirectoryId);
        } else {
            return review.title.contains(search);
        }
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
