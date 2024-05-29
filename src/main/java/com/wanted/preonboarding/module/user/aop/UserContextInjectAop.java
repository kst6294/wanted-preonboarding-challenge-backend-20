package com.wanted.preonboarding.module.user.aop;

import com.wanted.preonboarding.module.product.dto.CreateProduct;
import com.wanted.preonboarding.module.user.core.UserContext;
import com.wanted.preonboarding.module.user.entity.Users;
import com.wanted.preonboarding.module.user.service.UserFindService;
import com.wanted.preonboarding.module.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class UserContextInjectAop {

    private final UserFindService userFindService;

    @Pointcut("execution(* com.wanted.preonboarding.module.product.service.ProductQueryServiceImpl.createProduct(..)) && args(createProduct)")
    private void userContextInjectPointCut(CreateProduct createProduct) {}

    @Before(value = "userContextInjectPointCut(createProduct)", argNames = "createProduct")
    public void setAuthentication(CreateProduct createProduct) {
        String email = SecurityUtils.currentUserEmail();
        Users user = userFindService.fetchUserEntity(email);
        userInject(createProduct, user);
    }

    private void userInject(UserContext userContext, Users user) {
        userContext.setUsers(user);
    }
}
