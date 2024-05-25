package com.wanted.preonboarding.document.utils;


import com.wanted.preonboarding.auth.controller.AuthController;
import com.wanted.preonboarding.auth.service.AuthTokenGenerateService;
import com.wanted.preonboarding.auth.service.TokenFetchService;
import com.wanted.preonboarding.auth.validator.PasswordCheckService;
import com.wanted.preonboarding.module.user.service.UserFindService;
import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@Disabled
@WebMvcTest(controllers = AuthController.class)
public abstract class ControllerTest {

    @MockBean
    protected AuthTokenGenerateService authTokenGenerateService;

    @MockBean
    protected UserFindService userFindService;

    @MockBean
    protected TokenFetchService tokenFetchService;

    @MockBean
    protected PasswordCheckService passwordCheckService;

}
