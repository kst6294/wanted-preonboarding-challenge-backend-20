package com.wanted.preonboarding.document.utils;


import com.wanted.preonboarding.auth.controller.AuthController;
import com.wanted.preonboarding.auth.service.AuthTokenGenerateService;
import com.wanted.preonboarding.auth.service.TokenFindService;
import com.wanted.preonboarding.auth.validator.PasswordChecker;
import com.wanted.preonboarding.module.order.controller.OrderController;
import com.wanted.preonboarding.module.order.service.OrderFindService;
import com.wanted.preonboarding.module.order.service.OrderQueryService;
import com.wanted.preonboarding.module.order.service.OrderUpdateServiceProvider;
import com.wanted.preonboarding.module.order.validator.LimitPurchaseValidator;
import com.wanted.preonboarding.module.order.validator.OrderLockChecker;
import com.wanted.preonboarding.module.order.validator.ProductStatusValidator;
import com.wanted.preonboarding.module.order.validator.strategy.OrderStatusTransition;
import com.wanted.preonboarding.module.order.validator.strategy.OrderStatusTransitionProvider;
import com.wanted.preonboarding.module.product.controller.ProductController;
import com.wanted.preonboarding.module.product.service.ProductFindService;
import com.wanted.preonboarding.module.product.service.ProductQueryService;
import com.wanted.preonboarding.module.user.service.UserFindService;
import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@Disabled
@WebMvcTest(controllers = {
        AuthController.class,
        ProductController.class,
        OrderController.class,
})
public abstract class ControllerTest extends SecuritySupportTest {

    @MockBean
    protected AuthTokenGenerateService authTokenGenerateService;

    @MockBean
    protected UserFindService userFindService;

    @MockBean
    protected TokenFindService tokenFindService;

    @MockBean
    protected PasswordChecker passwordChecker;

    @MockBean
    protected ProductQueryService productQueryService;

    @MockBean
    protected ProductFindService productFindService;

    @MockBean
    protected OrderQueryService orderQueryService;

    @MockBean
    protected OrderLockChecker orderLockChecker;

    @MockBean
    protected LimitPurchaseValidator limitPurchaseValidator;

    @MockBean
    protected ProductStatusValidator productStatusValidator;

    @MockBean
    protected OrderUpdateServiceProvider orderUpdateServiceProvider;

    @MockBean
    protected OrderStatusTransitionProvider orderStatusTransitionProvider;

    @MockBean
    protected OrderStatusTransition orderStatusTransition;

    @MockBean
    protected OrderFindService orderFindService;


}
