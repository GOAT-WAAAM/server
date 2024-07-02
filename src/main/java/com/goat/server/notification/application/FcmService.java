package com.goat.server.notification.application;

import com.goat.server.notification.dto.FcmSendDto;
import com.goat.server.notification.dto.FcmSendDeviceDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface FcmService {
    void sendMessageTo(FcmSendDto fcmSendDto) throws IOException;

    List<FcmSendDeviceDto> selectFcmSendList();
}
