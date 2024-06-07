package com.wantedmarket.Item.exception;

import com.wantedmarket.global.exception.exception.BaseException;
import com.wantedmarket.member.exception.MemberErrorCode;
import lombok.Getter;

@Getter
public class ItemException extends BaseException {
    private final ItemErrorCode errorCode;
    private final String description;

    public ItemException(ItemErrorCode errorCode) {
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }
}
