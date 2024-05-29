package com.wanted.market.global.common.code;

import lombok.Getter;

@Getter
public enum ResponseCode {

    SUCCESS(2000, "response success", "응답에 성공했습니다."),
    SERVER_ERROR(5000, "server error", "요청에 실패했습니다."),
    BAD_REQUEST(4010, "bad request", "요청값을 확인해주세요."),
    NO_AUTHORIZATION(4030, "no authorization", "인증에 실패했습니다."),
    NO_PERMISSION(4030, "no permission", "권한이 없습니다."),
    ;

    private int status;
    private String label;
    private String message;

    ResponseCode(int status, String label, String message) {
        this.status = status;
        this.label = label;
        this.message = message;
    }
}
