package com.goat.server.global;

import static com.goat.server.mypage.fixture.UserFixture.USER_USER;

import com.goat.server.global.util.JwtTokenProvider;
import com.goat.server.global.util.filter.UserAuthentication;
import com.goat.server.mypage.domain.type.Role;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.core.context.SecurityContextHolder;


//나중에 spring security 적용 및 spring rest docs 사용시 공통 설정을 위해 미리 분리
@MockBean(JpaMetamodelMappingContext.class)
public class CommonControllerTest {

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void setUp() {
        UserAuthentication userAuthentication =
                new UserAuthentication(USER_USER.getUserId(), null, Role.USER.getAuthority());
        SecurityContextHolder.getContext().setAuthentication(userAuthentication);
    }
}