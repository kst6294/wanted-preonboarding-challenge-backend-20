package org.example.preonboarding;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.preonboarding.config.security.CustomAuthenticationManager;
import org.example.preonboarding.config.security.SecurityConfig;
import org.example.preonboarding.config.security.TokenProvider;
import org.example.preonboarding.member.service.MemberService;
import org.example.preonboarding.member.util.MemberUtil;
import org.example.preonboarding.order.controller.OrderController;
import org.example.preonboarding.order.service.OrderService;
import org.example.preonboarding.product.controller.ProductController;
import org.example.preonboarding.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@Import(SecurityConfig.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = {
        OrderController.class,
        ProductController.class,
}
)
public abstract class ControllerTestWithSecuritySupport {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private CustomAuthenticationManager customAuthenticationManager;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected MemberUtil memberUtil;

    @MockBean
    protected OrderService orderService;

    @MockBean
    protected ProductService productService;

    @MockBean
    protected MemberService memberService;

    protected String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
