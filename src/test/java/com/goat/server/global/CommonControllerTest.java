package com.goat.server.global;


import com.goat.server.global.util.filter.UserAuthentication;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;


//나중에 spring security 적용 및 spring rest docs 사용시 공통 설정을 위해 미리 분리
@MockBean(JpaMetamodelMappingContext.class)
public class CommonControllerTest {

    @BeforeEach
    public void setUp() throws ServletException, IOException {
        UserAuthentication userAuthentication = new UserAuthentication("testUser", null, null);
        SecurityContextHolder.getContext().setAuthentication(userAuthentication);
    }
}