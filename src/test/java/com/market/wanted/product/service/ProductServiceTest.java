package com.market.wanted.product.service;

import com.market.wanted.member.entity.Member;
import com.market.wanted.member.repository.MemberRepository;
import com.market.wanted.product.dto.ProductDto;
import com.market.wanted.product.entity.Product;
import com.market.wanted.product.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductService productService;


    @Test
    void productFindAll() {

        List<ProductDto> productDtos = productService.findAll();
        assertThat(productDtos.size()).isEqualTo(3);
    }

    @Test
    void findDto() {
        ProductDto productDto = productService.findDtoById(1L);

        assertThat(productDto).isNotNull();
        assertThat(productDto.getProductId()).isEqualTo(1L);

    }

}