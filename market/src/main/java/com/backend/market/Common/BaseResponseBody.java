package com.backend.market.Common;
import lombok.Getter;
import lombok.Setter;

/**
 * 서버 요청에대한 기본 응답값(바디) 정의.
 */
@Getter
@Setter
public class BaseResponseBody {

    String message = null;

    Integer statusCode = null;

    public BaseResponseBody() {}

    public BaseResponseBody(Integer statusCode){
        this.statusCode = statusCode;
    }

    public BaseResponseBody(Integer statusCode, String message){
        this.statusCode = statusCode;
        this.message = message;
    }

    public static BaseResponseBody of(Integer statusCode, String message) {
        BaseResponseBody body = new BaseResponseBody();
        body.message = message;
        body.statusCode = statusCode;
        return body;
    }
}