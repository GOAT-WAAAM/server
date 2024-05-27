package com.goat.server.directory.presentation;


import static com.goat.server.directory.fixture.DirectoryFixture.PARENT_DIRECTORY1;
import static com.goat.server.directory.fixture.DirectoryFixture.PARENT_DIRECTORY2;
import static com.goat.server.mypage.fixture.UserFixture.USER_USER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.goat.server.directory.dto.response.DirectoryResponse;
import com.goat.server.directory.dto.response.DirectoryResponseList;
import com.goat.server.global.CommonControllerTest;
import com.goat.server.directory.application.DirectoryService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(DirectoryController.class)
class DirectoryControllerTest extends CommonControllerTest {

    @MockBean
    private DirectoryService directoryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("과목, 폴더 정보 가져 오기")
    void getDirectoryListTest() throws Exception {
        //given
        DirectoryResponseList subjectResponseList =
                DirectoryResponseList.from(
                        List.of(DirectoryResponse.from(PARENT_DIRECTORY1), DirectoryResponse.from(PARENT_DIRECTORY2)));

        given(directoryService.getDirectoryList(USER_USER.getUserId()))
                .willReturn(subjectResponseList);

        //when
        ResultActions resultActions = mockMvc.perform(get("/subject/{userId}", USER_USER.getUserId()))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results.directoryResponseList[0].directoryId").value(PARENT_DIRECTORY1.getDirectoryId()))
                .andExpect(jsonPath("$.results.directoryResponseList[1].directoryId").value(PARENT_DIRECTORY2.getDirectoryId()));
    }
}