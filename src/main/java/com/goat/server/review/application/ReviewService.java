package com.goat.server.review.application;

import com.goat.server.review.dto.response.ReviewSimpleResponse;
import com.goat.server.review.repository.ReviewRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    /**
     * 폴더에 속한 리뷰 목록 조회
     */
    public List<ReviewSimpleResponse> getReviewSimpleResponseList(Long directoryId) {
        return reviewRepository.findByDirectory_DirectoryId(directoryId).stream()
                .map(ReviewSimpleResponse::from)
                .toList();
    }
}
