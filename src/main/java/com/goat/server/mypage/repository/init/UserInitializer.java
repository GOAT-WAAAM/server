package com.goat.server.mypage.repository.init;

import com.goat.server.global.domain.type.OauthProvider;
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

@Slf4j
@RequiredArgsConstructor
@LocalDummyDataInit
public class UserInitializer implements ApplicationRunner {

    private final UserRepository userRepository;

    public static final User DUMMY_USER = User.builder()
            .email("userImail")
            .role(Role.USER)
            .nickname("user")
            .goal("user go home")
            .provider(OauthProvider.KAKAO)
            .build();

    public static final User DUMMY_ADMIN = User.builder()
            .email("adminImail")
            .role(Role.ADMIN)
            .nickname("admin")
            .goal("admin go home")
            .provider(OauthProvider.KAKAO)
            .build();

    @Override
    public void run(ApplicationArguments args) {

        if (userRepository.count() > 0) {
            log.info("[User]더미 데이터 존재");
        } else {
            List<User> memberList = new ArrayList<>();

            memberList.add(DUMMY_USER);
            memberList.add(DUMMY_ADMIN);

            userRepository.saveAll(memberList);
        }
    }
}
