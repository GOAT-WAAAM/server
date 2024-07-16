package com.goat.server.auth.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goat.server.auth.application.AuthService;
import com.goat.server.auth.application.OAuthLoginService;
import com.goat.server.auth.dto.request.OnBoardingRequest;
import com.goat.server.auth.dto.response.ReIssueSuccessResponse;
import com.goat.server.auth.dto.response.SignUpSuccessResponse;
import com.goat.server.global.CommonControllerTest;
import com.goat.server.global.util.jwt.Tokens;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static com.goat.server.mypage.fixture.UserFixture.USER_USER;
import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest extends CommonControllerTest {

    @MockBean
    private OAuthLoginService OAuthLoginService;

    @MockBean
    private AuthService authService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("카카오 소셜 로그인 테스트")
    void kakaoSocialLogin() throws Exception {
        //given
        final String oauthProvider = "KAKAO";
        final String kakaoAccessToken = "thisIsmockAccessToken";

        given(OAuthLoginService.socialLogin(oauthProvider, kakaoAccessToken))
                .willReturn(SignUpSuccessResponse.of(Tokens.builder()
                        .accessToken("accessToken")
                        .refreshToken("refreshToken")
                        .build(), USER_USER, 0L));

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/goat/auth/login/{provider}", oauthProvider)
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

    @Test
    @DisplayName("회원탈퇴 테스트")
    void withdraw() throws Exception {
        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/goat/auth/withdraw")
                .header("Authorization", "accessToken"));

        //then
        resultActions
                .andExpect(status().isOk());
    }

}