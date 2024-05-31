package com.goat.server.auth.presentation;

import com.goat.server.auth.application.AuthService;
import com.goat.server.auth.application.KakaoSocialService;
import com.goat.server.auth.dto.response.ReIssueSuccessResponse;
import com.goat.server.auth.dto.response.SignUpSuccessResponse;
import com.goat.server.global.CommonControllerTest;
import com.goat.server.global.util.Tokens;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
}