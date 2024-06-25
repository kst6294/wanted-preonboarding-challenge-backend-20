package com.wanted.market.product.exception;

import com.wanted.market.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ProductErrorCode implements ErrorCode {
    STATUS_NOT_ON_SALE(HttpStatus.BAD_REQUEST, "판매중인 상품이 아닙니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."),
    NO_RESERVED_PRODUCT(HttpStatus.NOT_FOUND, "예약중인 상품이 없습니다."),
    OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "상품 재고가 없습니다.");

    private HttpStatus httpStatus;
    private String message;

    ProductErrorCode(HttpStatus httpStatus, String message){
        this.httpStatus = httpStatus;
        this.message = message;
    }

}
