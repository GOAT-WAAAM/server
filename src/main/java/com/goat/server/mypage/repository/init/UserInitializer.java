package com.goat.server.mypage.repository.init;

import com.goat.server.auth.domain.type.OAuthProvider;
import com.goat.server.global.util.LocalDummyDataInit;
import com.goat.server.mypage.domain.User;
import com.goat.server.mypage.domain.type.Role;
import com.goat.server.mypage.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

@Slf4j
@RequiredArgsConstructor
@LocalDummyDataInit
@Order(1)
public class UserInitializer implements ApplicationRunner {

    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) {

        if (userRepository.count() > 0) {
            log.info("[User]더미 데이터 존재");
        } else {
            List<User> memberList = new ArrayList<>();

            User DUMMY_GUEST = User.builder()
                    .email("guestEmail")
                    .role(Role.GUEST)
                    .nickname("guest")
                    .goal("guest go home")
                    .provider(OAuthProvider.KAKAO)
                    .build();

            User DUMMY_USER = User.builder()
                    .email("userEmail")
                    .role(Role.USER)
                    .nickname("user")
                    .goal("user go home")
                    .fcmToken("c5z1BZ-J4UIEv58an4DPTK:APA91bGBItmR0XevTVL4wUJqOTW4vESdyhpkdrNu1scBDPV-rBAc31GsASTvK3hSSGrIGs9X9zijLdJqpe9dZbHI2oTdQHyZLNlhxFCJYb08L99vJMWPYsoaJQux88SKKQWoXYRuzVZf")
                    .provider(OAuthProvider.KAKAO)
                    .build();

            User DUMMY_ADMIN = User.builder()
                    .email("adminEmail")
                    .role(Role.ADMIN)
                    .nickname("admin")
                    .goal("admin go home")
                    .provider(OAuthProvider.KAKAO)
                    .build();

            memberList.add(DUMMY_GUEST);
            memberList.add(DUMMY_USER);
            memberList.add(DUMMY_ADMIN);

            userRepository.saveAll(memberList);
        }
    }
}
