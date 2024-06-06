//package com.goat.server.mypage.application;
//
//import com.goat.server.mypage.repository.UserRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//import java.util.Optional;
//
//import static com.goat.server.mypage.fixture.UserFixture.USER_MYPAGEUSER;
//import static com.goat.server.mypage.fixture.UserFixture.USER_USER;
//import static com.goat.server.subject.fixture.SubjectFixture.SUBJECT1;
//import static com.goat.server.subject.fixture.SubjectFixture.SUBJECT2;
//import static org.mockito.BDDMockito.given;
//
//@ExtendWith(MockitoExtension.class)
//public class MypageServiceTest {
//
//    @InjectMocks
//    private MypageService mypageService;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Test
//    @DisplayName("유저 정보와 전공 가져오기 테스트")
//    void getUsersWithMajors(){
//        //given
//        given(userRepository.findById(USER_MYPAGEUSER.getUserId())).willReturn(Optional.of(USER_MYPAGEUSER));
//        given(userRepository.findUserWithMajors(USER_MYPAGEUSER))
//                .willReturn;
//
//        //when
//
//        //then
//    }
//}
