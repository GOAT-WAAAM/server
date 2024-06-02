package com.goat.server.mypage.fixture;

import com.goat.server.global.domain.type.OauthProvider;
import com.goat.server.mypage.domain.User;
import com.goat.server.mypage.domain.type.Grade;
import com.goat.server.mypage.domain.type.Role;
import com.goat.server.mypage.domain.type.School;
import org.springframework.test.util.ReflectionTestUtils;

public class UserFixture {

    public static final User USER_GUEST = User.builder()
            .email("guestImail")
            .role(Role.GUEST)
            .nickname("guest")
            .goal("guest go home")
            .provider(OauthProvider.KAKAO)
            .build();

    public static final User USER_USER = User.builder()
            .email("userImail")
            .role(Role.USER)
            .nickname("user")
            .goal("user go home")
            .provider(OauthProvider.KAKAO)
            .build();

    public static final User USER_ADMIN = User.builder()
            .email("adminImail")
            .role(Role.ADMIN)
            .nickname("admin")
            .goal("admin go home")
            .provider(OauthProvider.KAKAO)
            .build();

    public static final User USER_MYPAGEUSER = User.builder()
            .email("mypagetestImail")
            .role(Role.USER)
            .nickname("mypagetestuser")
            .grade(Grade.FIRST)
            .school(School.ELEMENTARY)
            .goal("this is mypage test")
            .provider(OauthProvider.KAKAO)
            .build();

    static {
        ReflectionTestUtils.setField(USER_GUEST, "userId", 1L);
        ReflectionTestUtils.setField(USER_USER, "userId", 2L);
        ReflectionTestUtils.setField(USER_ADMIN, "userId", 3L);
        ReflectionTestUtils.setField(USER_MYPAGEUSER, "userId", 4L);
    }
}
