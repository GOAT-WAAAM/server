package com.goat.server.notification.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goat.server.mypage.domain.User;
import com.goat.server.mypage.exception.UserNotFoundException;
import com.goat.server.mypage.repository.UserRepository;
import com.goat.server.notification.domain.type.PushType;
import com.goat.server.notification.dto.fcm.*;
import com.goat.server.notification.presentation.FcmApiClient;
import com.goat.server.review.domain.Review;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

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

    @Override
    public void sendMessageTo(Review review, PushType pushType) throws IOException {

        String message = makeMessageFromReview(review.getId(), review.getUser().getUserId(), pushType);

        try {
            fcmApiClient.sendMessage(projectName, "Bearer " + getAccessToken(), message);
        } catch (Exception e) {
            log.error("[-] FCM 전송 오류 :: " + e.getMessage());
        }
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


    private String makeMessageFromReview(Long reviewId, Long userId, PushType pushType) throws JsonProcessingException {

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        String deviceToken = Objects.requireNonNull(user.getFcmToken());

        String title = user.getNickname() + pushType.getTitle();
        String body = pushType.getBody();

        ObjectMapper om = new ObjectMapper();
        FcmMessageDto fcmMessageDto = FcmMessageDto.builder()
                .message(FcmMessageDto.Message.builder()
                        .token(deviceToken)
                        .notification(FcmMessageDto.Message.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(null)
                                .build()
                        )
                        .data(FcmMessageDto.Message.Data.builder()
                                .reviewId(reviewId.toString())
                                .build())
                        .build())
                .validateOnly(false)
                .build();

        return om.writeValueAsString(fcmMessageDto);
    }
}
