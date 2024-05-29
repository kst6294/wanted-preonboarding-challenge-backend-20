package com.wanted.preonboarding.module.order.validator;

import com.wanted.preonboarding.module.exception.product.NotFoundProductException;
import com.wanted.preonboarding.module.order.dto.CreateOrder;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.product.service.ProductFindService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductStatusValidator implements ConstraintValidator<ValidProductStatus, CreateOrder> {

    protected static final String PRODUCT_FILED = "productId";
    protected static final String OUT_OF_STOCK_ERR_MESSAGE = "이미 예약중 또는 판매완료 된 상품 입니다. ";
    private final ProductFindService productFindService;

    @Override
    public boolean isValid(CreateOrder createOrder, ConstraintValidatorContext context) {
        if (createOrder == null) {
            return false;
        }

        try {
            Product product = productFindService.fetchProductEntity(createOrder.getProductId());

            if (!product.getProductStatus().isOnStock()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(OUT_OF_STOCK_ERR_MESSAGE)
                        .addConstraintViolation();
                return false;
            }
            return true;
        } catch (NotFoundProductException e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addConstraintViolation();
            return false;
        }
    }

}
