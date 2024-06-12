package com.example.wantedmarketapi.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 400
    BAD_REQUEST("입력값이 올바르지 않습니다. 다시 시도해주세요."),

    // 401
    UN_AUTHENTICATION("인증 되지 않은 사용자입니다."),
    UN_AUTHORIZATION("인증 권한이 없습니다."),

    // 403
    NOT_FOUND("존재하지 않는 경로입니다."),

    // 500
    SERVICE_UNAVAILABLE("일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.") // 장애 상황

    ;

    private final String errorMsg;

    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }

}
