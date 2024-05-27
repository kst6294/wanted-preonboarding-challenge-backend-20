package com.wanted.preonboarding.document.utils;


import com.wanted.preonboarding.auth.controller.AuthController;
import com.wanted.preonboarding.auth.service.AuthTokenGenerateService;
import com.wanted.preonboarding.auth.service.TokenFetchService;
import com.wanted.preonboarding.auth.validator.PasswordChecker;
import com.wanted.preonboarding.module.product.controller.ProductController;
import com.wanted.preonboarding.module.product.service.ProductFetchService;
import com.wanted.preonboarding.module.product.service.ProductQueryService;
import com.wanted.preonboarding.module.product.service.ProductQueryServiceImpl;
import com.wanted.preonboarding.module.user.service.UserFindService;
import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@Disabled
@WebMvcTest(controllers = {
        AuthController.class,
        ProductController.class
})
public abstract class ControllerTest extends SecuritySupportTest {

    @MockBean
    protected AuthTokenGenerateService authTokenGenerateService;

    @MockBean
    protected UserFindService userFindService;

    @MockBean
    protected TokenFetchService tokenFetchService;

    @MockBean
    protected PasswordChecker passwordChecker;

    @MockBean
    protected ProductQueryService productQueryService;

    @MockBean
    protected ProductFetchService productFetchService;


}
