package wanted.market.domain.product.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;

import wanted.market.domain.ControllerTestSupport;

import wanted.market.domain.product.controller.dto.request.ProductRegisterRequest;
import wanted.market.domain.product.repository.entity.Product;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static wanted.market.domain.product.repository.entity.ReservationStatus.SALE;

class ProductControllerTest extends ControllerTestSupport {

    @Test
    @DisplayName("제품정보를 입력받아 제품을 등록한다.")
    void registerProduct() throws Exception {
        // given
        ProductRegisterRequest request = createProductRegisterRequest("라면", 1000, 1);

        // when & then
        mockMvc.perform(
                        post("/api/v1/product/register")
                                .header("Authorization", "Bearer " + accessToken)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @Test
    @DisplayName("페이징 처리가 된 제품의 목록을 조회한다.")
    void findProductList() throws Exception {
        // given
        ProductRegisterRequest request1 = createProductRegisterRequest("라면", 1000, 1);
        ProductRegisterRequest request2 = createProductRegisterRequest("담배", 4500, 1);
        ProductRegisterRequest request3 = createProductRegisterRequest("커피", 2000, 1);
        ProductRegisterRequest request4 = createProductRegisterRequest("과자", 1500, 1);

        productService.register(request1.toService(email));
        productService.register(request2.toService(email));
        productService.register(request3.toService(email));
        productService.register(request4.toService(email));

        // when & then
        mockMvc.perform(
                        get("/api/v1/product/list")
                                .queryParam("page", "0")
                ).andDo(print())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @Test
    @DisplayName("원하는 제품을 상세조회한다.")
    void findProductDetail() throws Exception {
        // given
        ProductRegisterRequest request = createProductRegisterRequest("라면", 1000, 1);
        Long id = productService.register(request.toService(email));

        // when & then
        mockMvc.perform(
                        get("/api/v1/product/detail")
                                .queryParam("productId", Long.toString(id))
                ).andDo(print())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    private static ProductRegisterRequest createProductRegisterRequest(String name, int price, int quantity) {
        return ProductRegisterRequest.builder()
                .name(name)
                .price(price)
                .quantity(quantity)
                .content("판매중입니다.")
                .build();
    }
}