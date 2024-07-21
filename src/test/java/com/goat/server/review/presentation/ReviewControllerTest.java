package com.goat.server.review.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goat.server.global.CommonControllerTest;
import com.goat.server.review.application.ReviewService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
public class ReviewControllerTest extends CommonControllerTest {

    @MockBean
    private ReviewService reviewService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("놓친 복습 조회 테스트")
    void getMissedReviewTest() throws Exception {

        //when
        ResultActions resultActions =
                mockMvc.perform(get("/goat/missed-review"))
                        .andDo(print());

        //then
        resultActions
                .andExpect(status().isOk());
    }
}
