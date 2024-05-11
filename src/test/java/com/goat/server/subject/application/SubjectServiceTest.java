package com.goat.server.subject.application;


import static com.goat.server.mypage.fixture.UserFixture.USER_USER;
import static com.goat.server.subject.fixture.SubjectFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.goat.server.mypage.repository.UserRepository;
import com.goat.server.subject.dto.response.SubjectResponse;
import com.goat.server.subject.dto.response.SubjectResponseList;
import com.goat.server.subject.fixture.SubjectFixture;
import com.goat.server.subject.repository.SubjectRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SubjectServiceTest {

    @InjectMocks
    private SubjectService subjectService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @Test
    @DisplayName("과목과 폴더 가져 오기 테스트")
    void getSubjectsAndDirectories() {
        //given
        given(userRepository.findById(USER_USER.getUserId())).willReturn(Optional.of(USER_USER));
        given(subjectRepository.findSubjectsAndDirectories(USER_USER))
                .willReturn(List.of(SUBJECT1, SUBJECT2));

        //when
        SubjectResponseList subjectsAndDirectories = subjectService.getSubjectsAndDirectories(USER_USER.getUserId());

        //then
        assertThat(subjectsAndDirectories.subjectResponseList())
                .extracting(SubjectResponse::subjectName)
                .containsExactly(SubjectFixture.SUBJECT1.getSubjectName(), SubjectFixture.SUBJECT2.getSubjectName());
    }
}