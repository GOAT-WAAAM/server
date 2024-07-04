package com.goat.server.notification.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goat.server.mypage.domain.User;
import com.goat.server.mypage.exception.UserNotFoundException;
import com.goat.server.mypage.repository.UserRepository;
import com.goat.server.notification.dto.fcm.*;
import com.goat.server.notification.presentation.FcmApiClient;
import com.goat.server.review.domain.Review;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.goat.server.mypage.exception.errorcode.MypageErrorCode.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmServiceImpl implements FcmService {

    private final UserRepository userRepository;

    private final FcmApiClient fcmApiClient;

    @Value("${fcm.project.name}")
    private String projectName;

    @Value("${fcm.config.path}")
    private String firebaseConfigPath;

    /**
     * 푸시 메시지 처리를 수행하는 비즈니스 로직
     *
     * @param fcmSendDto 모바일에서 전달받은 Object
     */
    @Override
    public void sendMessageTo(FcmSendDto fcmSendDto) throws IOException {

        String message = makeMessage(fcmSendDto);

        try {
            fcmApiClient.sendMessage(projectName, "Bearer " + getAccessToken(), message);
        } catch (Exception e) {
            log.error("[-] FCM 전송 오류 :: " + e.getMessage());
            log.error("[-] 오류 발생 토큰 :: [" + fcmSendDto.token() + "]");
            log.error("[-] 오류 발생 메시지 :: [" + fcmSendDto.body() + "]");
        }
    }

    @Override
    public void sendMessageTo(Review review) throws IOException {

        String message = makeMessageFromReview(review.getUser().getUserId());

        try {
            fcmApiClient.sendMessage(projectName, "Bearer " + getAccessToken(), message);
        } catch (Exception e) {
            log.error("[-] FCM 전송 오류 :: " + e.getMessage());
        }
    }



    /**
     * FCM 전송 디바이스 리스트 조회 (공통 알림을 받을 클라이언트 선택 메서드)
     */
    @Override
    @Transactional(readOnly = true)
    public List<FcmSendDeviceDto> selectFcmSendList() {

        List<User> userList = userRepository.findAll();

        List<FcmSendDeviceDto> sendList = userList.stream()
                .filter(user -> Optional.ofNullable(user.getFcmToken()).isPresent())
                .map(user -> FcmSendDeviceDto.builder()
                        .deviceToken(user.getFcmToken())
                        .build())
                .collect(Collectors.toList());

        return sendList;
    }

    /**
     * Firebase Admin SDK의 비공개 키를 참조하여 Bearer 토큰을 발급
     *
     * @return Bearer token
     */
    private String getAccessToken() throws IOException {

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(Arrays.asList("https://www.googleapis.com/auth/firebase.messaging"));

        googleCredentials.refreshIfExpired();

        return googleCredentials.getAccessToken().getTokenValue();
    }

    /**
     * FCM 전송 정보를 기반으로 메시지를 구성. (Object -> String)
     *
     * @param fcmSendDto FcmSendDto
     * @return String
     */
    private String makeMessage(FcmSendDto fcmSendDto) throws JsonProcessingException {

        ObjectMapper om = new ObjectMapper();
        FcmMessageDto fcmMessageDto = FcmMessageDto.builder()
                .message(Message.builder()
                        .token(fcmSendDto.token())
                        .notification(Notification.builder()
                                .title(fcmSendDto.title())
                                .body(fcmSendDto.body())
                                .image(null)
                                .build()
                        ).build())
                .validateOnly(false)
                .build();

        return om.writeValueAsString(fcmMessageDto);
    }

    private String makeMessageFromReview(Long userId) throws JsonProcessingException {

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        String deviceToken = Optional.ofNullable(user.getFcmToken())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        ObjectMapper om = new ObjectMapper();
        FcmMessageDto fcmMessageDto = FcmMessageDto.builder()
                .message(Message.builder()
                        .token(deviceToken)
                        .notification(Notification.builder()
                                .title("🔔복습할 시이에요!")
                                .body("지금 복습하면 잊어버리지 않아요")
                                .image(null)
                                .build()
                        ).build())
                .validateOnly(false)
                .build();

        return om.writeValueAsString(fcmMessageDto);
    }
}
