package com.goat.server.mypage.fixture;

import com.goat.server.auth.domain.type.OAuthProvider;
import com.goat.server.global.domain.ImageInfo;
import com.goat.server.mypage.domain.User;
import com.goat.server.mypage.domain.type.Role;
import org.springframework.test.util.ReflectionTestUtils;

public class UserFixture {

    public static final User USER_GUEST = User.builder()
            .email("guestImail")
            .role(Role.GUEST)
            .nickname("guest")
            .goal("guest go home")
            .provider(OAuthProvider.KAKAO)
            .build();

    public static final User USER_USER = User.builder()
            .email("userImail")
            .role(Role.USER)
            .nickname("user")
            .goal("user go home")
            .provider(OAuthProvider.KAKAO)
            .fcmToken("c5z1BZ-J4UIEv58an4DPTK:APA91bGBItmR0XevTVL4wUJqOTW4vESdyhpkdrNu1scBDPV-rBAc31GsASTvK3hSSGrIGs9X9zijLdJqpe9dZbHI2oTdQHyZLNlhxFCJYb08L99vJMWPYsoaJQux88SKKQWoXYRuzVZf")
            .imageInfo(new ImageInfo("imageFileName", "imageFolderName", "imageUrl"))
            .build();

    public static final User USER_ADMIN = User.builder()
            .email("adminImail")
            .role(Role.ADMIN)
            .nickname("admin")
            .goal("admin go home")
            .provider(OAuthProvider.KAKAO)
            .build();

    public static final User USER_MYPAGEUSER = User.builder()
            .email("mypagetestImail")
            .role(Role.USER)
            .nickname("mypagetestuser")
            .goal("this is mypage test")
            .provider(OAuthProvider.KAKAO)
            .build();

    static {
        ReflectionTestUtils.setField(USER_GUEST, "userId", 1L);
        ReflectionTestUtils.setField(USER_USER, "userId", 2L);
        ReflectionTestUtils.setField(USER_ADMIN, "userId", 3L);
        ReflectionTestUtils.setField(USER_MYPAGEUSER, "userId", 4L);
    }
}
