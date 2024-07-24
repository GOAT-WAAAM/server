package com.goat.server.auth.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.goat.server.auth.domain.type.OAuthProvider;
import com.goat.server.directory.repository.DirectoryRepository;
import com.goat.server.mypage.repository.UserRepository;
import com.goat.server.notification.application.FcmServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import static org.assertj.core.api.Assertions.assertThat;


import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureWireMock(port = 0)
@TestPropertySource(properties = {
        "oauth.kakao.service.api_url=http://localhost:${wiremock.server.port}",
        "oauth.naver.service.api_url=http://localhost:${wiremock.server.port}"
})
@DisplayName("OAuth 로그인 테스트")
class OAuthLoginServiceTest {

    @Autowired
    private OAuthLoginService OAuthLoginService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WireMockServer wireMockServer;

    @MockBean
    private FcmServiceImpl fcmService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DirectoryRepository directoryRepository;

    @BeforeEach
    void setUp() {
        wireMockServer.stop();
        wireMockServer.start();
    }

    @AfterEach
    void afterEach() {
        wireMockServer.resetAll();
    }

    @Test
    @DisplayName("카카오_소셜로그인_테스트")
    void KakaoSocialLoginTest() throws JsonProcessingException {
        //given
        final String oauthProvider = "KAKAO";
        final String kakaoAccessToken = "thisIsmockAccessToken";
        final String expectedResponse = objectMapper.writeValueAsString(
                Map.of(
                        "id", 1231241,
                        "kakao_account", Map.of(
                                "profile", Map.of(
                                        "nickname", "nickname",
                                        "profile_image_url", "http://example.com/profile.jpg"
                                )
                        )
                )
        );

        //wireMock 사용
        stubFor(get(urlEqualTo("/v2/user/me"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(expectedResponse))
        );


        //when
        OAuthLoginService.socialLogin(oauthProvider, kakaoAccessToken);

        //then
        assertNotNull(userRepository.findBySocialId(String.valueOf(1231241)));
        assertThat(userRepository.findBySocialId(String.valueOf(1231241)).get().getProvider()).isEqualTo(OAuthProvider.KAKAO);
        assertThat(directoryRepository.findTrashDirectoryByUser(1L).get().getTitle()).isEqualTo("trash_directory");
        assertThat(directoryRepository.findStorageDirectoryByUser(1L).get().getTitle()).isEqualTo("storage_directory");
    }

    @Test
    @DisplayName("네이버_소셜로그인_테스트")
    void naverSocialLoginTest() throws JsonProcessingException {
        //given
        final String oauthProvider = "NAVER";
        final String naverAccessToken = "thisIsmockAccessToken";
        final String expectedResponse = objectMapper.writeValueAsString(
                Map.of(
                        "resultCode", "00",
                        "message", "success",
                        "response", Map.of(
                                "id", "1203443",
                                "nickname", "nickname",
                                "profile_image", "http://example.com/profile.jpg"
                        )
                )
        );

        //wireMock 사용
        stubFor(get(urlEqualTo("/v1/nid/me"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(expectedResponse))
        );


        //when
        OAuthLoginService.socialLogin(oauthProvider, naverAccessToken);

        //then
        assertNotNull(userRepository.findBySocialId("1203443"));
        assertThat(userRepository.findBySocialId("1203443").get().getProvider()).isEqualTo(OAuthProvider.NAVER);
    }

}
