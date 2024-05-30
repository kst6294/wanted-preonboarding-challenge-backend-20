package com.example.wantedmarketapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.wantedmarketapi.domain.Product;
import com.example.wantedmarketapi.domain.enums.ProductStatus;
import com.example.wantedmarketapi.dto.response.ProductResponseDto.*;
import com.example.wantedmarketapi.exception.GlobalErrorCode;
import com.example.wantedmarketapi.exception.custom.ProductException;
import com.example.wantedmarketapi.repository.ProductRepository;
import com.example.wantedmarketapi.service.impl.ProductQueryServiceImpl;
import com.example.wantedmarketapi.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductQueryServiceImplTest {

    @InjectMocks
    private ProductQueryServiceImpl productQueryService;

    @Mock
    private ProductRepository productRepository;

    @Test
    void getProductList_ShouldReturnProductList() {

        //Given
        Product product1 = TestUtil.createProductWithReflection();
        TestUtil.setField(product1, "name", "Test Product1");
        TestUtil.setField(product1, "price", 1000);
        TestUtil.setField(product1, "productStatus", ProductStatus.SALE);
        TestUtil.setField(product1, "reservationStatus", false);

        Product product2 = TestUtil.createProductWithReflection();
        TestUtil.setField(product2, "name", "Test Product2");
        TestUtil.setField(product2, "price", 2000);
        TestUtil.setField(product2, "productStatus", ProductStatus.SALE);
        TestUtil.setField(product2, "reservationStatus", false);

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        // When
        List<GetProductResponse> result = productQueryService.getProductList();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test Product1", result.get(0).getName());
        assertEquals(1000, result.get(0).getPrice());
        assertEquals("Test Product2", result.get(1).getName());
        assertEquals(2000, result.get(1).getPrice());

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductDetails_ShouldReturnProductDetails() {

        // Given
        Long productId = 1L;
        Product product = TestUtil.createProductWithReflection();
        TestUtil.setField(product, "name", "Test Product");
        TestUtil.setField(product, "price", 1000);
        TestUtil.setField(product, "productStatus", ProductStatus.SALE);
        TestUtil.setField(product, "reservationStatus", false);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // When
        GetProductDetailsResponse result = productQueryService.getProductDetails(productId);

        // Then
        assertNotNull(result);
        assertEquals("Test Product", result.getName());
        assertEquals(1000, result.getPrice());
        assertEquals(ProductStatus.SALE, result.getProductStatus());
        assertEquals(false, result.getReservationStatus());

        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void getProductDetails_ShouldThrowException_WhenProductNotFound() {

        // Given
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // When & Then
        ProductException exception = assertThrows(ProductException.class, () -> {
            productQueryService.getProductDetails(productId);
        });

        assertEquals(GlobalErrorCode.PRODUCT_NOT_FOUND, exception.getErrorCode());
        verify(productRepository, times(1)).findById(productId);
    }
}
