package kr.co.wanted.market.common.global.dto;

import lombok.Getter;

import java.util.Map;

@Getter
public class ErrorResponse {

    private final String code;

    private final String message;

    private final Map<String, String> basicValidations;


    private ErrorResponse(String code,
                          String message,
                          Map<String, String> basicValidations) {

        this.code = code;
        this.message = message;
        this.basicValidations = basicValidations;
    }


    public static ErrorResponse of(String code,
                                   String message,
                                   Map<String, String> basicValidations) {

        return new ErrorResponse(code, message, basicValidations);
    }

}
