package com.goat.server.notification.application;

import com.goat.server.notification.domain.type.PushType;

import com.goat.server.review.domain.Review;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface FcmService {
    void sendMessageTo(Review review, PushType pushType) throws IOException;
}
