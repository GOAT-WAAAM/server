package com.goat.server.review.util;

import com.goat.server.review.domain.Review;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ReviewShuffleStrategy {
    public List<Review> shuffle(List<Review> reviews) {
        final List<Review> result = new ArrayList<>(reviews);
        Collections.shuffle(result);
        return result;
    }
}