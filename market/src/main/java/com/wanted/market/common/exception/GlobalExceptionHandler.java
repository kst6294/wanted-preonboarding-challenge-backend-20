package com.wanted.market.common.exception;

import com.wanted.market.common.dto.ApiResponse;
import com.wanted.market.member.exception.MemberErrorCode;
import com.wanted.market.member.exception.MemberException;
import com.wanted.market.order.exception.OrderErrorCode;
import com.wanted.market.order.exception.OrderException;
import com.wanted.market.product.exception.ProductErrorCode;
import com.wanted.market.product.exception.ProductException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<ApiResponse<Void>> handleProductException(ProductException ex) {
        ProductErrorCode errorCode = ex.getProductErrorCode();
        ApiResponse<Void> errorResponse = ApiResponse.error(
                errorCode.getHttpStatus().value(),
                errorCode.getMessage()
        );
        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<ApiResponse<Void>> handleOrderException(OrderException ex) {
        OrderErrorCode errorCode = ex.getOrderErrorCode();
        ApiResponse<Void> errorResponse = ApiResponse.error(
                errorCode.getHttpStatus().value(),
                errorCode.getMessage()
        );
        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ApiResponse<Void>> handleMemberException(MemberException ex) {
        MemberErrorCode errorCode = ex.getMemberErrorCode();
        ApiResponse<Void> errorResponse = ApiResponse.error(
                errorCode.getHttpStatus().value(),
                errorCode.getMessage()
        );
        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }

}
