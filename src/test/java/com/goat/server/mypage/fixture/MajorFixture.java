//package com.goat.server.mypage.fixture;
//
//import com.goat.server.mypage.domain.Major;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import static com.goat.server.mypage.repository.init.UserInitializer.DUMMY_GUEST;
//import static com.goat.server.mypage.repository.init.UserInitializer.DUMMY_MYPAGEUSER;
//
//public class MajorFixture {
//
//    public static final Major DUMMY_MAJOR1 = Major.builder()
//            .majorName("dummyMajor1")
//            .user(DUMMY_GUEST)
//            .build();
//
//    public static final Major DUMMY_MAJOR2 = Major.builder()
//            .majorName("dummyMajor2")
//            .user(DUMMY_MYPAGEUSER)
//            .build();
//
//    public static final Major DUMMY_MAJOR3 = Major.builder()
//            .majorName("dummyMajor3")
//            .user(DUMMY_MYPAGEUSER)
//            .build();
//
//    static {
//        ReflectionTestUtils.setField(DUMMY_MAJOR1, "majorId", 1L);
//        ReflectionTestUtils.setField(DUMMY_MAJOR2, "majorId", 2L);
//        ReflectionTestUtils.setField(DUMMY_MAJOR3, "majorId", 3L);
//    }
//}
