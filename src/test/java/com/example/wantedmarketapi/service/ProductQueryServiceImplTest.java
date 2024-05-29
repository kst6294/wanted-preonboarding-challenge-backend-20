package com.example.wantedmarketapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.wantedmarketapi.domain.Product;
import com.example.wantedmarketapi.domain.enums.ProductStatus;
import com.example.wantedmarketapi.dto.request.ProductRequestDto.*;
import com.example.wantedmarketapi.dto.response.ProductResponseDto.*;
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
}
