package com.goat.server.auth.presentation;

import com.goat.server.auth.application.AuthService;
import com.goat.server.auth.application.KakaoSocialService;
import com.goat.server.auth.dto.response.ReIssueSuccessResponse;
import com.goat.server.auth.dto.response.SignUpSuccessResponse;
import com.goat.server.global.CommonControllerTest;
import com.goat.server.global.domain.type.AccessToken;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(MockitoExtension.class)
class AuthControllerTest extends CommonControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    @Mock
    private KakaoSocialService kakaoSocialService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    @DisplayName("소셜 로그인 테스트")
    void socialLogin() throws Exception {
        //given
        final String kakaoAccessToken = "thisIsmockAccessToken";

        given(kakaoSocialService.socialLogin(kakaoAccessToken))
                .willReturn(SignUpSuccessResponse.From(AccessToken.builder()
                        .accessToken("accessToken")
                        .refreshToken("refreshToken")
                        .build()));

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/goat/auth/login/{provider}", "KAKAO")
                .header("Authorization", kakaoAccessToken));


        log.info("resultActions: {}", resultActions.andReturn().getResponse().getContentAsString());

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
                .willReturn(ReIssueSuccessResponse.From(AccessToken.builder()
                        .accessToken("accessToken")
                        .refreshToken("refreshToken")
                        .build()));

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/goat/auth/refreshToken")
                .header("Authorization", refreshToken));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results.accessToken").value("accessToken"))
                .andExpect(jsonPath("$.results.refreshToken").value("refreshToken"));

    }
}