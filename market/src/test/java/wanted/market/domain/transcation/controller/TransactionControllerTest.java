package wanted.market.domain.transcation.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import wanted.market.domain.ControllerTestSupport;
import wanted.market.domain.product.repository.entity.Product;
import wanted.market.domain.product.service.dto.request.ProductRegisterServiceRequest;
import wanted.market.domain.transcation.controller.dto.TransactionCancelRequest;
import wanted.market.domain.transcation.controller.dto.TransactionCreateRequest;
import wanted.market.domain.transcation.repository.entity.Transaction;
import wanted.market.domain.transcation.service.dto.request.TransactionCreateServiceRequest;
import wanted.market.domain.transcation.service.dto.response.TransactionCreateResponse;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static wanted.market.domain.product.repository.entity.ReservationStatus.RESERVATION;
import static wanted.market.domain.product.repository.entity.ReservationStatus.SALE;

class TransactionControllerTest extends ControllerTestSupport {

    @Test
    @DisplayName("제품을 토대로 거래를 시작하면 제품의 수량을 확인하고 필요할 경우 예약상태를 변경한다.")
    void createTransaction() throws Exception {
        // given
        int price = 1000;
        Long productId = productService.register(createProductRequest("라면", email, price, 1, "라면입니다."));
        TransactionCreateRequest request = TransactionCreateRequest.builder()
                .price(price)
                .productId(productId)
                .build();

        // when & then
        mockMvc.perform(
                        post("/api/v1/transaction/create")
                                .header("Authorization", "Bearer " + accessToken)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @Test
    @DisplayName("거래를 진행할 상대방의 역대 거래 정보를 조회한다.")
    void findTransactionWithMember() throws Exception {
        // given
        Long productId = productService.register(createProductRequest("라면", email, 1000, 1, "라면입니다."));
        productService.register(createProductRequest("커피", email2, 3000, 1, "커피입니다."));

        TransactionCreateResponse transaction = transactionService.createTransaction(TransactionCreateServiceRequest.builder()
                .productId(productId)
                .price(1000)
                .email(email2)
                .build());

        // when & then
        mockMvc.perform(
                        get("/api/v1/transaction/list")
                                .header("Authorization", "Bearer " + accessToken)
                                .queryParam("email", email2)
                ).andDo(print())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @Test
    @DisplayName("거래가 취소되는 경우, 해당 수량이 다시 반환되어야 하고, 만약 제품의 예약상태가 '예약중' 인 경우 '판매중' 으로 변경된다.")
    void bringBackQuantity() throws Exception {
        // given
        Long productId = productService.register(createProductRequest("라면", email, 1000, 1, "라면입니다."));

        TransactionCreateResponse transaction = transactionService.createTransaction(TransactionCreateServiceRequest.builder()
                .productId(productId)
                .price(1000)
                .email(email2)
                .build());

        TransactionCancelRequest request = TransactionCancelRequest.builder()
                .transactionId(transaction.getTransactionId())
                .build();

        // when & then
        mockMvc.perform(
                        post("/api/v1/transaction/cancel")
                                .header("Authorization", "Bearer " + accessToken)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @Test
    @DisplayName("판매자도 거래를 승낙하고 거래가 완료된다.")
    void completeTransaction() throws Exception {
        // given
        Long productId = productService.register(createProductRequest("라면", email, 1000, 1, "라면입니다."));

        TransactionCreateResponse transaction = transactionService.createTransaction(TransactionCreateServiceRequest.builder()
                .productId(productId)
                .price(1000)
                .email(email2)
                .build());

        // when & then
        mockMvc.perform(
                        get("/api/v1/transaction/complete")
                                .header("Authorization", "Bearer " + accessToken)
                                .queryParam("transactionId", String.valueOf(transaction.getTransactionId()))
                ).andDo(print())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    private static ProductRegisterServiceRequest createProductRequest(String name, String email, int price, int quantity, String content) {
        return ProductRegisterServiceRequest.builder()
                .name(name)
                .email(email)
                .price(price)
                .reservationStatus(SALE)
                .quantity(quantity)
                .content(content)
                .build();
    }
}