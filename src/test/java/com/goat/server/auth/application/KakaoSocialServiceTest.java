package com.goat.server.auth.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
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
        "oauth.kakao.service.api_url=http://localhost:${wiremock.server.port}"
})
@DisplayName("OAuth 로그인 테스트")
class KakaoSocialServiceTest {

    @Autowired
    private KakaoSocialService kakaoSocialService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WireMockServer wireMockServer;

    @MockBean
    private FcmServiceImpl fcmService;

    private ObjectMapper objectMapper = new ObjectMapper();

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
    void socialLoginTest() throws JsonProcessingException {
        //given
        final String kakaoAccessToken = "thisIsmockAccessToken";
        final String expectedResponse = objectMapper.writeValueAsString(
                Map.of(
                        "id", 1231241,
                        "kakao_account", Map.of(
                                "profile", Map.of(
                                        "nickname", "nickname"
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
        kakaoSocialService.socialLogin(kakaoAccessToken);

        //then
        assertNotNull(userRepository.findBySocialId(String.valueOf(1231241)));
        assertThat(directoryRepository.findTrashDirectoryByUser(1L).get().getTitle()).isEqualTo("trash_directory");
        assertThat(directoryRepository.findStorageDirectoryByUser(1L).get().getTitle()).isEqualTo("storage_directory");
    }

}
