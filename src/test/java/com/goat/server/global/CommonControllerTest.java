package com.goat.server.global;

import static com.goat.server.mypage.fixture.UserFixture.USER_USER;

import com.goat.server.global.config.SchedulerConfiguration;
import com.goat.server.global.util.AuthenticationUtil;
import com.goat.server.global.util.jwt.JwtTokenProvider;
import com.goat.server.global.config.TestSecurityConfig;
import com.goat.server.global.util.filter.UserAuthentication;
import com.goat.server.mypage.domain.type.Role;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.context.SecurityContextHolder;

//나중에 spring security 적용 및 spring rest docs 사용시 공통 설정을 위해 미리 분리
@Import(TestSecurityConfig.class)
public class CommonControllerTest {

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private AuthenticationUtil authenticationUtil;

    @MockBean
    private SchedulerConfiguration schedulerConfiguration;

    @BeforeEach
    public void setUp() {
        UserAuthentication userAuthentication =
                new UserAuthentication(USER_USER.getUserId(), null, Role.USER.getAuthority());
        SecurityContextHolder.getContext().setAuthentication(userAuthentication);
    }
}