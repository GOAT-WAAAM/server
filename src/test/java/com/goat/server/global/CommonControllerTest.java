package com.goat.server.global;


import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;

//나중에 spring security 적용 및 spring rest docs 사용시 공통 설정을 위해 미리 분리
@MockBean(JpaMetamodelMappingContext.class)
public class CommonControllerTest {

    @BeforeEach
    public void setUp() {

    }
}