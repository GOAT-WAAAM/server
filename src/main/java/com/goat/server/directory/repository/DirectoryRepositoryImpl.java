package com.goat.server.directory.repository;

import static com.goat.server.directory.application.type.SortType.DIRECTORY_SORT_MAP;
import static com.goat.server.directory.domain.QDirectory.directory;

import com.goat.server.directory.application.type.SortType;
import com.goat.server.directory.dto.response.DirectoryResponse;
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
public class DirectoryRepositoryImpl implements DirectoryRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<DirectoryResponse> findAllDirectoryResponseBySortAndSearch(
            Long userId, Long parentDirectoryId, List<SortType> sort, String search) {
        return query
                .select(Projections.constructor(DirectoryResponse.class,
                        directory.id,
                        directory.title,
                        directory.directoryColor,
                        directory.directoryIcon))
                .from(directory)
                .where(directory.user.userId.eq(userId), isSearchExpression(parentDirectoryId, search))
                .orderBy(sortExpression(sort))
                .fetch();
    }

    //search가 null이면 parentDirectoryId로 검색, 아니면 search로 검색 - search 존재 -> 전체 검색
    private BooleanExpression isSearchExpression(Long parentDirectoryId, String search) {
        if (StringUtils.hasLength(search)) {
            return parentDirectoryFindExpression(parentDirectoryId);
        } else {
            return directory.title.contains(search);
        }
    }

    private BooleanExpression parentDirectoryFindExpression(Long parentDirectoryId) {
        if (parentDirectoryId == 0) {
            return directory.parentDirectory.isNull();
        } else {
            return directory.parentDirectory.id.eq(parentDirectoryId);
        }
    }



    // 기본 정렬 - 이름 오름차순
    private OrderSpecifier<?>[] sortExpression(List<SortType> sort) {
        if (sort == null || sort.isEmpty()) {
            return new OrderSpecifier[]{directory.title.asc()};
        }
        return sort.stream()
                .map(sortType -> DIRECTORY_SORT_MAP.getOrDefault(sortType, dir -> dir.title.asc()).apply(directory))
                .toArray(OrderSpecifier[]::new);
    }
}
