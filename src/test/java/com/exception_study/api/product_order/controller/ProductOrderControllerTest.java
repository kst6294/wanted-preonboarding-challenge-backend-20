package com.exception_study.api.product_order.controller;

import com.exception_study.api.product.dto.ProductDto;
import com.exception_study.api.product_order.dto.ProductOrderDto;
import com.exception_study.api.product_order.dto.response.HistoryResponse;
import com.exception_study.api.product_order.service.ProductOrderService;
import com.exception_study.api.user_account.dto.UserAccountDto;
import com.exception_study.global.exception.ErrorCode;
import com.exception_study.global.exception.StudyApplicationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("컨트롤러 테스트 - ProductOrder")
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ProductOrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductOrderService productOrderService;

    @Autowired
    ObjectMapper objectMapper;

    /**
     * 기본적인 예외상황(없는 유저, 없는 상품, 예약된 상품, 품절인 상품) 등에 대한 테스트는 예약 로직에서 일괄적으로 확인했음.
     * 서비스 테스트 로직에서 상세한 동작을 테스트 할 예정임. 따라서 다른 로직에 겹치는 예외 상황에 대한 테스트 로직은 작성하지 않고 진행함.
     */


    @DisplayName("상품 주문 예약 요청을 하면 성공 응답을 반환한다.")
    @Test
    @WithMockUser
    public void givenAuthenticatedUser_whenReserving_thenReturnsSuccess() throws Exception {
        //given
        UserAccountDto seller = UserAccountDto.of("test", "1234", "테스터");
        UserAccountDto buyer = UserAccountDto.of("test2", "1234", "테스터2");
        ProductDto testProduct = ProductDto.of(1L, "테스트 상품", 1000, "판매중", 3, seller);
        ProductOrderDto testProductOrder = ProductOrderDto.of(1L, testProduct, 1000, seller, buyer, "판매중", "판매중");

        //when&then
        when(productOrderService.reserve(testProduct.getId(), buyer.getUserId()))
                .thenReturn(testProductOrder);

        mockMvc.perform(put("/api/v1/order/{product_id}/reserve", testProduct.getId())).andExpect(status().isOk());

    }

    @DisplayName("없는 유저가 상품 주문 예약을 요청하면 예외를 반환한다.")
    @Test
    @WithAnonymousUser
    public void givenNotExistsUser_whenReserving_thenReturnsException() throws Exception {
        //given
        UserAccountDto seller = UserAccountDto.of("test", "1234", "테스터");
        UserAccountDto buyer = UserAccountDto.of("test2", "1234", "테스터2");
        ProductDto testProduct = ProductDto.of(1L, "테스트 상품", 1000, "판매중", 3, seller);
        ProductOrderDto testProductOrder = ProductOrderDto.of(1L, testProduct, 1000, seller, buyer, "판매중", "판매중");

        //when&then
        doThrow(
                new StudyApplicationException(ErrorCode.USER_NOT_FOUND)
        ).when(productOrderService).reserve(anyLong(), anyString());

        mockMvc.perform(put("/api/v1/order/{product_id}/reserve", testProduct.getId()))
                .andExpect(status().is(ErrorCode.USER_NOT_FOUND.getStatus().value()));

    }

    @DisplayName("없는 상품에 상품 주문 예약을 요청하면 예외를 반환한다.")
    @Test
    @WithAnonymousUser
    public void givenNotExistsProduct_whenReserving_thenReturnsException() throws Exception {
        //given
        UserAccountDto seller = UserAccountDto.of("test", "1234", "테스터");
        UserAccountDto buyer = UserAccountDto.of("test2", "1234", "테스터2");
        ProductDto testProduct = ProductDto.of(1L, "테스트 상품", 1000, "판매중", 3, seller);
        ProductOrderDto testProductOrder = ProductOrderDto.of(1L, testProduct, 1000, seller, buyer, "판매중", "판매중");

        //when&then
        doThrow(
                new StudyApplicationException(ErrorCode.PRODUCT_NOT_FOUND)
        ).when(productOrderService).reserve(anyLong(), anyString());

        mockMvc.perform(put("/api/v1/order/{product_id}/reserve", testProduct.getId()))
                .andExpect(status().is(ErrorCode.PRODUCT_NOT_FOUND.getStatus().value())
                );
    }

    @DisplayName("이미 예약한 상품에 상품 주문 예약을 요청하면 예외를 반환한다.")
    @Test
    @WithAnonymousUser
    public void givenAlreadyReserved_whenReserving_thenReturnsException() throws Exception {
        //given
        UserAccountDto seller = UserAccountDto.of("test", "1234", "테스터");
        UserAccountDto buyer = UserAccountDto.of("test2", "1234", "테스터2");
        ProductDto testProduct = ProductDto.of(1L, "테스트 상품", 1000, "판매중", 3, seller);
        ProductOrderDto testProductOrder = ProductOrderDto.of(1L, testProduct, 1000, seller, buyer, "판매중", "판매중");

        //when&then
        doThrow(
                new StudyApplicationException(ErrorCode.PRODUCT_ALREADY_RESERVED)
        ).when(productOrderService).reserve(anyLong(), anyString());

        mockMvc.perform(put("/api/v1/order/{product_id}/reserve", testProduct.getId()))
                .andExpect(status().is(ErrorCode.PRODUCT_ALREADY_RESERVED.getStatus().value())
                );
    }

    @DisplayName("품절된 상품에 상품 주문 예약을 요청하면 예외를 반환한다.")
    @Test
    @WithAnonymousUser
    public void givenSoldOutProduct_whenReserving_thenReturnsException() throws Exception {
        //given
        UserAccountDto seller = UserAccountDto.of("test", "1234", "테스터");
        UserAccountDto buyer = UserAccountDto.of("test2", "1234", "테스터2");
        ProductDto testProduct = ProductDto.of(1L, "테스트 상품", 1000, "판매중", 3, seller);
        ProductOrderDto testProductOrder = ProductOrderDto.of(1L, testProduct, 1000, seller, buyer, "판매중", "판매중");

        //when&then
        doThrow(
                new StudyApplicationException(ErrorCode.PRODUCT_SOLD_OUT)
        ).when(productOrderService).reserve(anyLong(), anyString());

        mockMvc.perform(put("/api/v1/order/{product_id}/reserve", testProduct.getId()))
                .andExpect(status().is(ErrorCode.PRODUCT_SOLD_OUT.getStatus().value())
                );
    }

    @DisplayName("예약 요청을 승인하면 성공 응답을 반환한다.")
    @Test
    @WithMockUser
    public void givenReservedProduct_whenApproving_thenReturnsSuccess() throws Exception {
        //given
        UserAccountDto seller = UserAccountDto.of("test", "1234", "테스터");
        UserAccountDto buyer = UserAccountDto.of("test2", "1234", "테스터2");
        ProductDto testProduct = ProductDto.of(1L, "테스트 상품", 1000, "판매중", 3, seller);
        ProductOrderDto testProductOrder = ProductOrderDto.of(1L, testProduct, 1000, seller, buyer, "판매중", "판매중");

        //when&then
        mockMvc.perform(put("/api/v1/order/{product_order_id}/approve", testProductOrder.getId()))
                .andExpect(status().isOk());
        verify(productOrderService).approve(anyLong(), any());

    }

    @DisplayName("본인의 상품이 아닌 상품의 예약을 승인하면 예외를 반환한다.")
    @Test
    @WithAnonymousUser
    public void givenInvalidPermission_whenApproving_thenReturnsException() throws Exception {
        //given
        UserAccountDto seller = UserAccountDto.of("test", "1234", "테스터");
        UserAccountDto buyer = UserAccountDto.of("test2", "1234", "테스터2");
        ProductDto testProduct = ProductDto.of(1L, "테스트 상품", 1000, "판매중", 3, seller);
        ProductOrderDto testProductOrder = ProductOrderDto.of(1L, testProduct, 1000, seller, buyer, "판매중", "판매중");

        //when&then
        doThrow(
                new StudyApplicationException(ErrorCode.INVALID_PERMISSION)
        ).when(productOrderService).approve(anyLong(),anyString());

        mockMvc.perform(put("/api/v1/order/{product_order_id}/approve", testProductOrder.getId()))
                .andExpect(status().is(ErrorCode.INVALID_PERMISSION.getStatus().value())
                );

    }

    @DisplayName("예약 승인된 주문을 구매 확정하면 성공 응답을 반환한다.")
    @Test
    @WithMockUser
    public void givenApprovedOrder_whenConfirming_thenReturnsSuccess() throws Exception {
        //given
        UserAccountDto seller = UserAccountDto.of("test", "1234", "테스터");
        UserAccountDto buyer = UserAccountDto.of("test2", "1234", "테스터2");
        ProductDto testProduct = ProductDto.of(1L, "테스트 상품", 1000, "판매중", 3, seller);
        ProductOrderDto testProductOrder = ProductOrderDto.of(1L, testProduct, 1000, seller, buyer, "판매중", "판매중");


        //when&then
        mockMvc.perform(put("/api/v1/order/{product_order_id}/confirm",testProductOrder.getId()))
                .andExpect(status().isOk());
        verify(productOrderService).confirm(anyLong(),any());
    }

    @DisplayName("구매,예약 내역을 조회하면 성공 응답을 반환한다.")
    @Test
    @WithMockUser
    public void givenAuthenticatedUser_whenRequestingHistory_thenReturnsSuccess() throws Exception {
        //given
        UserAccountDto seller = UserAccountDto.of("test", "1234", "테스터");
        UserAccountDto buyer = UserAccountDto.of("test2", "1234", "테스터2");
        ProductDto testProduct = ProductDto.of(1L, "테스트 상품", 1000, "판매중", 3, seller);
        ProductOrderDto boughtOrder = ProductOrderDto.of(1L, testProduct, 1000, seller, buyer, "완료", "완료");
        ProductOrderDto reservedOrder = ProductOrderDto.of(2L, testProduct, 1000, seller, buyer, "예약중", "예약중");

        //when&then
        when(productOrderService.history(buyer.getUserId()))
                .thenReturn(
                        HistoryResponse.of(
                                List.of(boughtOrder),
                                List.of(reservedOrder)
                        )
                );

        mockMvc.perform(get("/api/v1/order/history"))
                .andExpect(status().isOk());
    }


}
