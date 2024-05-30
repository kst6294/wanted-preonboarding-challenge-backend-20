package com.exception_study.global.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {
    private String resultCode;
    private T result;

    public static <T> ResponseDto<T> success() {
        return new ResponseDto<T>("SUCCESS", null);
    }

    public static <T> ResponseDto<T> success(T result) {
        return new ResponseDto<T>("SUCCESS", result);
    }

    public static ResponseDto<Void> error(String resultCode) {
        return new ResponseDto<Void>(resultCode, null);
    }

    public String toStream() {
        if (result == null) {
            return "{" +
                    "\"resultCode\":" + "\"" + resultCode + "\"," +
                    "\"result\":" + null +
                    "}";
        }
        return "{" +
                "\"resultCode\":" + "\"" + resultCode + "\"," +
                "\"result\":" + "\"" + result + "\"," +
                "}";
    }

}
