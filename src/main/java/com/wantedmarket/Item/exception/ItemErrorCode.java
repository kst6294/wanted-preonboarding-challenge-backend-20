package com.wantedmarket.Item.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ItemErrorCode {
    INVALID_ID("유효하지 않은 제품 번호입니다.");
    private final String description;
}
