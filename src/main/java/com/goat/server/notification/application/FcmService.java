package com.goat.server.notification.application;

import com.goat.server.notification.dto.fcm.FcmSendDto;
import com.goat.server.notification.dto.fcm.FcmSendDeviceDto;
import com.goat.server.review.domain.Review;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface FcmService {
    void sendMessageTo(FcmSendDto fcmSendDto) throws IOException;

    void sendMessageTo(Review review) throws IOException;

    List<FcmSendDeviceDto> selectFcmSendList();
}
