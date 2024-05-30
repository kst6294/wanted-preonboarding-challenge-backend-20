package com.example.wantedmarketapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.wantedmarketapi.domain.Product;
import com.example.wantedmarketapi.domain.enums.ProductStatus;
import com.example.wantedmarketapi.domain.member.Member;
import com.example.wantedmarketapi.dto.request.ProductRequestDto.*;
import com.example.wantedmarketapi.exception.custom.ProductException;
import com.example.wantedmarketapi.repository.ProductRepository;
import com.example.wantedmarketapi.service.impl.ProductCommandServiceImpl;
import com.example.wantedmarketapi.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class ProductCommandServiceImplTest {

    @InjectMocks
    private ProductCommandServiceImpl productCommandService;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void addProduct_NewProduct_ShouldSucceed() {
        // Given
        String name = "Test Product";
        Integer price = 1000;
        Long memberId = 1L;
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Member member = TestUtil.createMemberWithReflection();
        TestUtil.setField(member, "id", memberId);

        CreateProductRequest request = new CreateProductRequest();
        TestUtil.setField(request, "name", name);
        TestUtil.setField(request, "price", price);

        // When
        Product result = productCommandService.createProduct(member, request);

        // Then
        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(price, result.getPrice());

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void setProductStatus_ValidProduct_ShouldSucceed() {
        // Given
        Long productId = 1L;
        Long memberId = 1L;
        ProductStatus initialStatus = ProductStatus.RESERVATION;
        ProductStatus newStatus = ProductStatus.DONE;

        Product product = TestUtil.createProductWithReflection();
        TestUtil.setField(product, "id", productId);

        // Member 객체 생성 및 초기화
        Member seller = TestUtil.createMemberWithReflection();
        TestUtil.setField(seller, "id", memberId);

        TestUtil.setField(product, "seller", seller);
        TestUtil.setField(product, "productStatus", initialStatus);

        when(productRepository.findByIdAndSellerId(productId, memberId)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Member member = TestUtil.createMemberWithReflection();
        TestUtil.setField(member, "id", memberId);

        // When
        Product result = productCommandService.setProductStatus(member, productId);

        // Then
        assertNotNull(result);
        assertEquals(newStatus, result.getProductStatus());

        verify(productRepository, times(1)).findByIdAndSellerId(productId, memberId);
        verify(productRepository, times(1)).save(product);
    }
}
