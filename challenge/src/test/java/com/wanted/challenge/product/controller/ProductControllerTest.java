package com.wanted.challenge.product.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.wanted.challenge.ControllerTestSupport;
import com.wanted.challenge.product.request.PurchaseRequest;
import com.wanted.challenge.product.request.RegisterRequest;
import com.wanted.challenge.security.WithAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class ProductControllerTest extends ControllerTestSupport {

    @Test
    @DisplayName("비회원은 제품 등록을 할 수 없다")
    void registerRequestOfAnonymous() throws Exception {

        // given
        RegisterRequest registerRequest = new RegisterRequest("name", 10_000, 1);

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .content(objectMapper.writeValueAsString(registerRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAccount
    @DisplayName("제품 등록")
    void registerRequest() throws Exception {

        // given
        RegisterRequest registerRequest = new RegisterRequest("name", 10_000, 1);

        given(productService.register(anyString(), any(), any(), anyLong()))
                .willReturn(1L);

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .content(objectMapper.writeValueAsString(registerRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("제품 구매는 회원만 가능하다")
    void purchaseRequestOfAnonymous() throws Exception {

        // given
        PurchaseRequest purchaseRequest = new PurchaseRequest(1L);

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/products/purchase")
                        .content(objectMapper.writeValueAsString(purchaseRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAccount
    @DisplayName("제품 구매")
    void purchaseRequest() throws Exception {

        // given
        PurchaseRequest purchaseRequest = new PurchaseRequest(1L);

        doNothing().when(productService)
                .purchase(anyLong(), anyLong());

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/products/purchase")
                        .content(objectMapper.writeValueAsString(purchaseRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
