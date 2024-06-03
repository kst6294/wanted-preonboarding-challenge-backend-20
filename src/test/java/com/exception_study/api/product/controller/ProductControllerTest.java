package com.exception_study.api.product.controller;

import com.exception_study.api.product.dto.ProductDto;
import com.exception_study.api.product.dto.response.DetailsWithHistoryResponse;
import com.exception_study.api.product.service.ProductService;
import com.exception_study.api.product_order.dto.ProductOrderDto;
import com.exception_study.api.user_account.dto.UserAccountDto;
import com.exception_study.api.user_account.dto.request.RegisterRequest;
import com.exception_study.global.exception.ErrorCode;
import com.exception_study.global.exception.StudyApplicationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("컨트롤러 테스트 - Product")
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("상품 목록을 조회하면 성공 응답을 반환한다.")
    @Test
    @WithAnonymousUser
    public void givenNothing_whenRequestingProductList_thenReturnsSuccess() throws Exception {
        //given
        Long id = 1L;
        String name = "테스트";
        int price = 1000;
        String status = "판매중";
        int quantity = 3;
        UserAccountDto seller = UserAccountDto.of("test", "1234", "테스터");

        //when&then
        when(productService.getList()).thenReturn(List.of(ProductDto.of(id,name,price,status,quantity,seller)));

        mockMvc.perform(get("/api/v1/products/list")).andExpect(status().isOk());
    }

    @DisplayName("상품 등록을 요청하면 성공 응답을 반환한다.")
    @Test
    @WithMockUser
    public void givenProductInfo_whenRegistering_thenReturnsSuccess() throws Exception {
        //given
        String userId = "tester";
        String name = "테스트";
        int price = 1000;
        int quantity = 3;
        RegisterRequest dto = RegisterRequest.of(name, price, quantity);

        //when&then
        mockMvc.perform(post("/api/v1/products/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(dto))
        ).andExpect(status().isOk());
        verify(productService).register(any(RegisterRequest.class),any());
    }

    @DisplayName("로그인하지 않은 유저가 등록 요청을 하면 예외를 반환한다.")
    @Test
    @WithAnonymousUser
    public void givenUnauthenticatedUser_whenRegistering_thenReturnsException() throws Exception {
        //given
        String userId = "tester";
        String name = "테스트";
        int price = 1000;
        int quantity = 3;
        RegisterRequest dto = RegisterRequest.of(name, price, quantity);
        //when&then
        doThrow(new StudyApplicationException(ErrorCode.USER_NOT_FOUND))
                .when(productService).register(any(RegisterRequest.class),any());

        mockMvc.perform(post("/api/v1/products/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(dto))
        ).andExpect(status().is(ErrorCode.USER_NOT_FOUND.getStatus().value()));
    }

    @DisplayName("상품 상세 요청을 하면 성공 응답을 반환한다.")
    @Test
    @WithAnonymousUser
    public void givenNothing_whenRequestingDetails_thenReturnsSuccess() throws Exception {
        //given
        Long id = 1L;
        String name = "테스트";
        int price = 1000;
        String status = "판매중";
        int quantity = 3;
        UserAccountDto seller = UserAccountDto.of("test", "1234", "테스터");

        //when&then
        when(productService.getDetails(id)).thenReturn(ProductDto.of((long) id,name,price,status,quantity,seller));

        mockMvc.perform(get("/api/v1/products/1/details")).andExpect(status().isOk());
    }

    @DisplayName("없는 상품을 조회하면 예외를 반환한다.")
    @Test
    @WithAnonymousUser
    public void givenNotExistsProducts_whenRequestingDetails_thenReturnsSuccess() throws Exception{
        //given
        Long id = 1L;
        //when&then
        doThrow(new StudyApplicationException(ErrorCode.PRODUCT_NOT_FOUND))
                .when(productService).getDetails(id);

        mockMvc.perform(get("/api/v1/products/1/details"))
                .andExpect(status().is(ErrorCode.PRODUCT_NOT_FOUND.getStatus().value()));
    }

    @DisplayName("상품과 거래내역 리스트를 조회하면 성공 응답을 반환한다.")
    @Test
    @WithMockUser
    public void givenAuthenticatedUser_whenRequestingDetailsWithHistory_thenReturnsSuccess() throws Exception{
        //given
        UserAccountDto seller = UserAccountDto.of("test", "1234", "테스터");
        UserAccountDto buyer = UserAccountDto.of("test2", "1234", "테스터2");
        ProductDto productDto = ProductDto.of(1L, "test", 1000, "판매중", 3, seller);
        //when&then
        when(productService.getDetails(1L,"test")).thenReturn(DetailsWithHistoryResponse.of(productDto, List.of(ProductOrderDto.of(productDto, 1000, seller, buyer, "판매중", "예약중"))));

        mockMvc.perform(get("/api/v1/products/1/details/authenticated")).andExpect(status().isOk());
    }

    @DisplayName("인증되지 않은 사용자가 상품과 거래내역 리스트를 조회하면 예외를 반환한다.")
    @Test
    @WithAnonymousUser
    public void givenUnauthenticatedUser_whenRequestingDetailsWithHistory_thenReturnsException() throws Exception {
        //given

        //when&then
        when(productService.getDetails(anyLong(),anyString())).thenThrow(new StudyApplicationException(ErrorCode.USER_NOT_FOUND));

        mockMvc.perform(get("/api/v1/products/1/details/authenticated"))
                .andExpect(status().is(ErrorCode.USER_NOT_FOUND.getStatus().value()));
    }

    @DisplayName("없는 상품의 상품과 거래내역 리스트를 조회하면 예외를 반환한다.")
    @Test
    @WithAnonymousUser
    public void givenNotExistsProduct_whenRequestingDetailsWithHistory_thenReturnsException() throws Exception {
        //given

        //when&then
        when(productService.getDetails(anyLong(),anyString())).thenThrow(new StudyApplicationException(ErrorCode.PRODUCT_NOT_FOUND));

        mockMvc.perform(get("/api/v1/products/1/details/authenticated"))
                .andExpect(status().is(ErrorCode.PRODUCT_NOT_FOUND.getStatus().value()));
    }






}
