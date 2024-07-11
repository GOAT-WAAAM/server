package com.goat.server.auth.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goat.server.auth.application.AuthService;
import com.goat.server.auth.application.KakaoSocialService;
import com.goat.server.auth.dto.OnBoardingRequest;
import com.goat.server.auth.dto.response.ReIssueSuccessResponse;
import com.goat.server.auth.dto.response.SignUpSuccessResponse;
import com.goat.server.global.CommonControllerTest;
import com.goat.server.global.util.jwt.Tokens;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest extends CommonControllerTest {

    @MockBean
    private KakaoSocialService kakaoSocialService;

    @MockBean
    private AuthService authService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("소셜 로그인 테스트")
    void socialLogin() throws Exception {
        //given
        final String kakaoAccessToken = "thisIsmockAccessToken";

        given(kakaoSocialService.socialLogin(kakaoAccessToken))
                .willReturn(SignUpSuccessResponse.from(Tokens.builder()
                        .accessToken("accessToken")
                        .refreshToken("refreshToken")
                        .build()));

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/goat/auth/login/{provider}", "KAKAO")
                .header("Authorization", kakaoAccessToken));


        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results.accessToken").value("accessToken"))
                .andExpect(jsonPath("$.results.refreshToken").value("refreshToken"));
    }

    @Test
    @DisplayName("토큰 재발급 테스트")
    void reIssueToken() throws Exception {
        //given
        final String refreshToken = "thisIsRefreshToken";

        given(authService.reIssueToken(refreshToken))
                .willReturn(ReIssueSuccessResponse.from(Tokens.builder()
                        .accessToken("accessToken")
                        .refreshToken("refreshToken")
                        .build()));

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/goat/auth/refresh-token")
                .header("Authorization", refreshToken));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results.accessToken").value("accessToken"))
                .andExpect(jsonPath("$.results.refreshToken").value("refreshToken"));

    }

    @Test
    @DisplayName("온보딩 회원정보 입력 테스트")
    void saveOnBoardingInfo() throws Exception {
        //given

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.patch("/goat/auth/info")
                .content(objectMapper.writeValueAsString(new OnBoardingRequest("nickname", "안녕하세요!", "fcmToken")))
                .contentType("application/json"));

        //then
        resultActions
                .andExpect(status().isOk());
    }

}