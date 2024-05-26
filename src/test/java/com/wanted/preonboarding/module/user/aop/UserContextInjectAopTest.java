package com.wanted.preonboarding.module.user.aop;

import com.wanted.preonboarding.data.product.ProductModuleHelper;
import com.wanted.preonboarding.document.utils.SecuritySupportTest;
import com.wanted.preonboarding.module.product.dto.CreateProduct;
import com.wanted.preonboarding.module.product.mapper.ProductMapper;
import com.wanted.preonboarding.module.product.repository.ProductRepository;
import com.wanted.preonboarding.module.product.service.ProductQueryService;
import com.wanted.preonboarding.module.product.service.ProductQueryServiceImpl;
import com.wanted.preonboarding.module.user.entity.Users;
import com.wanted.preonboarding.module.user.service.UserFindService;
import org.aopalliance.aop.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.security.test.context.support.WithMockUser;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserContextInjectAopTest extends SecuritySupportTest {

    @Mock
    private UserFindService userFindService;

    @InjectMocks
    private UserContextInjectAop userContextInjectAop;

    private CreateProduct createProduct;
    private Users user;



    @BeforeEach
    void setUp() {
        createProduct = ProductModuleHelper.toCreateProductWithUsers();
        user = createProduct.getUsers();
        when(userFindService.fetchUserEntity(anyString())).thenReturn(user);
        securityUserMockSetting(user);
    }




    @Test
    @DisplayName("UserContext가 설정된 상태에서 Product 생성")
    @WithMockUser(username = "test@example.com")
    void createProductWithUserContext() {
        userContextInjectAop.setAuthentication(createProduct);
        assertThat(createProduct.getUsers()).isEqualTo(user);
    }


}