package com.goat.server.global;


import com.goat.server.global.util.AuthenticationUtil;
import com.goat.server.global.util.jwt.JwtTokenProvider;
import com.goat.server.global.util.filter.UserAuthentication;
import com.goat.server.mypage.domain.type.Role;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;


//나중에 spring security 적용 및 spring rest docs 사용시 공통 설정을 위해 미리 분리
@MockBean(JpaMetamodelMappingContext.class)
public class CommonControllerTest {

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private AuthenticationUtil authenticationUtil;

    @BeforeEach
    public void setUp() throws ServletException, IOException {
        UserAuthentication userAuthentication = new UserAuthentication(123, null, Role.USER.getAuthority());
        SecurityContextHolder.getContext().setAuthentication(userAuthentication);
    }
}