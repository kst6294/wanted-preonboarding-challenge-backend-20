package com.wanted.preonboarding.module.order.validator;

import com.wanted.preonboarding.data.order.OrderModuleHelper;
import com.wanted.preonboarding.data.product.ProductFactory;
import com.wanted.preonboarding.data.product.ProductModuleHelper;
import com.wanted.preonboarding.module.exception.product.NotFoundProductException;
import com.wanted.preonboarding.module.order.dto.CreateOrder;
import com.wanted.preonboarding.module.product.dto.CreateProduct;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.product.service.ProductFindService;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductStatusValidatorTest {

    @InjectMocks
    private ProductStatusValidator productStatusValidator;

    @Mock
    private ProductFindService productFindService;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nodeBuilderCustomizableContext;


    Product product;
    CreateOrder createOrder;

    @BeforeEach
    void setUp() {
        CreateProduct createProductWithUsers = ProductModuleHelper.toCreateProductWithUsers();
        product = ProductFactory.generateProduct(createProductWithUsers);
        createOrder = OrderModuleHelper.toCreateOrder(product);
    }


    @Test
    @DisplayName("재고가 있는 제품")
    void validProductStatus() {
        // given
        when(productFindService.fetchProductEntity(anyLong())).thenReturn(product);
        // when
        boolean result = productStatusValidator.isValid(createOrder, null);
        // then
        assertTrue(result);
        verify(productFindService).fetchProductEntity(createOrder.getProductId());
    }

    @Test
    @DisplayName("예약중인 상품")
    void invalidProductStatus() {
        // given
        product.doBooking();
        when(productFindService.fetchProductEntity(anyLong())).thenReturn(product);

        when(constraintValidatorContext.buildConstraintViolationWithTemplate(anyString()))
                .thenReturn(constraintViolationBuilder);
        when(constraintViolationBuilder.addPropertyNode(anyString()))
                .thenReturn(nodeBuilderCustomizableContext);

        // when
        boolean result = productStatusValidator.isValid(createOrder, constraintValidatorContext);

        // then
        assertFalse(result);
        verify(productFindService).fetchProductEntity(createOrder.getProductId());
        verify(constraintValidatorContext).disableDefaultConstraintViolation();
        verify(constraintValidatorContext).buildConstraintViolationWithTemplate(anyString());
        verify(constraintViolationBuilder).addPropertyNode(anyString());
        verify(nodeBuilderCustomizableContext).addConstraintViolation();
    }

    @Test
    @DisplayName("존재하지 않는 제품")
    void productNotFound() {

        // given
        when(productFindService.fetchProductEntity(anyLong())).thenThrow(new NotFoundProductException(1L));

        when(constraintValidatorContext.buildConstraintViolationWithTemplate(anyString()))
                .thenReturn(constraintViolationBuilder);
        when(constraintViolationBuilder.addPropertyNode(anyString()))
                .thenReturn(nodeBuilderCustomizableContext);

        // when
        boolean result = productStatusValidator.isValid(createOrder, constraintValidatorContext);

        // then
        assertFalse(result);
        verify(productFindService).fetchProductEntity(createOrder.getProductId());
        verify(constraintValidatorContext).disableDefaultConstraintViolation();
        verify(constraintValidatorContext).buildConstraintViolationWithTemplate(anyString());
        verify(constraintViolationBuilder).addPropertyNode(anyString());
        verify(nodeBuilderCustomizableContext).addConstraintViolation();
    }



}