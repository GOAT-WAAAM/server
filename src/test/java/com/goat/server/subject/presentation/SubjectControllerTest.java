package com.goat.server.subject.presentation;


import static com.goat.server.mypage.fixture.UserFixture.USER_USER;
import static com.goat.server.subject.fixture.SubjectFixture.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.goat.server.global.CommonControllerTest;
import com.goat.server.subject.application.SubjectService;
import com.goat.server.subject.dto.response.SubjectResponse;
import com.goat.server.subject.dto.response.SubjectResponseList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(SubjectController.class)
class SubjectControllerTest extends CommonControllerTest {

    @MockBean
    private SubjectService subjectService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("과목, 폴더 정보 가져 오기")
    void getSubjectsAndDirectories() throws Exception {
        //given
        SubjectResponseList subjectResponseList =
                SubjectResponseList.from(List.of(SubjectResponse.from(SUBJECT1), SubjectResponse.from(SUBJECT2)));

        given(subjectService.getSubjectsAndDirectories(USER_USER.getUserId()))
                .willReturn(subjectResponseList);

        //when
        ResultActions resultActions = mockMvc.perform(get("/subject/{userId}", USER_USER.getUserId()))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results.subjectResponseList[0].subjectName").value(SUBJECT1.getSubjectName()))
                .andExpect(jsonPath("$.results.subjectResponseList[1].subjectName").value(SUBJECT2.getSubjectName()));
    }
}