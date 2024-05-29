package com.example.wantedmarketapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.example.wantedmarketapi.domain.Product;
import com.example.wantedmarketapi.domain.member.Member;
import com.example.wantedmarketapi.dto.request.ProductRequestDto.*;
import com.example.wantedmarketapi.dto.response.ProductResponseDto.*;
import com.example.wantedmarketapi.repository.ProductRepository;
import com.example.wantedmarketapi.security.provider.JwtAuthProvider;
import com.example.wantedmarketapi.service.impl.MemberCommandServiceImpl;
import com.example.wantedmarketapi.service.impl.ProductCommandServiceImpl;
import com.example.wantedmarketapi.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


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
}
