package com.challenge.market.product.controller;

import com.challenge.market.product.dto.ProductResponse;
import com.challenge.market.product.service.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    MockMvc mockMvc;

    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService;

    @BeforeEach
    public void init(){
        // Spring context 를 로드하지 않으므로 속도가 빨라 단일 컨트롤러에 직접 주입한다.
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }


 /*   @Test
    @DisplayName("제품 목록 조회 실패. 등록된 제품 목록이 없음")
    void testFindProductListFail() throws Exception {
        // given
        String url = "/products";

        // when
        ResultActions action = mockMvc.perform(get(url));

        // then
        action.andExpect(status().isBadRequest());
    }*/

    @Test
    @DisplayName("제품 목록 조회 성공")
    void testFindProductListSuccess() throws Exception {
        // given
        String url = "/products";
        doReturn(productResponseList())
                .when(productService)
                .findAll();

        // when
        ResultActions actions = mockMvc.perform(get(url));

        // then
        actions.andExpect(status().isOk());
    }

    private List<ProductResponse> productResponseList() {
        return Arrays.asList(
                ProductResponse.builder().build(),
                ProductResponse.builder().build(),
                ProductResponse.builder().build()
        );

    }

    @Test
    @DisplayName("제품 상세 페이지 조회")
    void testFindProductDetailFail() throws Exception {
        // given
        // 요청 url
        String url= "/products/{id}";

        // when
        ResultActions actions = mockMvc.perform(get(url));

        // then
        actions.andExpect(status().isBadRequest());

    }


}
