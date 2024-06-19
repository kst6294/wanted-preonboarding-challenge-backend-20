package com.wanted.market.member.exception;

import com.wanted.market.product.exception.ProductErrorCode;

public class MemberException extends RuntimeException {

    private MemberErrorCode memberErrorCode;

    public MemberException(MemberErrorCode memberErrorCode) {
    }
}
