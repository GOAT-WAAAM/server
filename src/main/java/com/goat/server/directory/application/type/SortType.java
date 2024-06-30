package com.goat.server.directory.application.type;

import com.goat.server.directory.domain.QDirectory;
import com.goat.server.review.domain.QReview;
import com.querydsl.core.types.OrderSpecifier;
import java.util.Map;
import java.util.function.Function;

public enum SortType {
    NAME_ASC,
    NAME_DESC,
    CREATED_AT,
    MOST_REVIEWED,
    ;

    public static final Map<SortType, Function<QDirectory, OrderSpecifier<?>>> DIRECTORY_SORT_MAP = Map.of(
            SortType.CREATED_AT, dir -> dir.createdDate.desc(),
            SortType.MOST_REVIEWED, dir -> dir.modifiedDate.desc(),
            SortType.NAME_ASC, dir -> dir.title.asc(),
            SortType.NAME_DESC, dir -> dir.title.desc()
    );

    public static final Map<SortType, Function<QReview, OrderSpecifier<?>>> REVIEW_SORT_MAP = Map.of(
            SortType.CREATED_AT, review -> review.createdDate.desc(),
            SortType.MOST_REVIEWED, review -> review.reviewCnt.desc(),
            SortType.NAME_ASC, review -> review.title.asc(),
            SortType.NAME_DESC, review -> review.title.desc()
    );
}
