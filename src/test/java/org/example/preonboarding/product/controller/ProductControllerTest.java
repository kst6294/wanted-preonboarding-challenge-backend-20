package org.example.preonboarding.product.controller;

import org.example.preonboarding.ControllerTestWithSecuritySupport;
import org.example.preonboarding.product.model.payload.request.ProductCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

import static org.example.preonboarding.product.model.enums.ProductSellingStatus.SELLING;
import static org.example.preonboarding.product.model.enums.ProductType.UNCLASSIFIED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ProductControllerTest extends ControllerTestWithSecuritySupport {

    @Test
    @DisplayName("로그인 후 제품 create")
    @WithMockUser(username = "admin", password = "admin", roles = "MEMBER")
    void createProductWithLogin() throws Exception {
        // given
        ProductCreateRequest productCreateRequest = ProductCreateRequest.builder()
                .name("testName")
                .productType(UNCLASSIFIED)
                .productSellingStatus(SELLING)
                .description("description")
                .price(1000)
                .sellingUserId("test")
                .build();

        // when then
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(productCreateRequest)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("로그인하지않으면 제품을 생성할 수 없다.")
    @WithAnonymousUser
    void createProductWithoutLogin() throws Exception {
        // given
        ProductCreateRequest productCreateRequest = ProductCreateRequest.builder()
                .name("testName")
                .productType(UNCLASSIFIED)
                .productSellingStatus(SELLING)
                .description("description")
                .price(1000)
                .sellingUserId("test")
                .build();

        // when then
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(productCreateRequest)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}