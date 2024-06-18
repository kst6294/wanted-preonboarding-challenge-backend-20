package com.example.wantedmarketapi.domain.product;

import com.example.wantedmarketapi.IntegrationTestSupport;
import com.example.wantedmarketapi.domain.product.Product.Status;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductServiceTest extends IntegrationTestSupport {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    @DisplayName("상품 등록")
    void registerProduct() {
        ProductCommand command = ProductCommand.builder()
                .name("상품이름")
                .price(10000)
                .userId(1L)
                .build();

        ProductInfo productInfo = productService.registerProduct(command);

        Assertions.assertThat(command.getName()).isEqualTo(productInfo.getName());
        Assertions.assertThat(command.getPrice()).isEqualTo(productInfo.getPrice());
        Assertions.assertThat(productInfo.getStatus()).isEqualTo(Status.PREPARE);
    }
}
