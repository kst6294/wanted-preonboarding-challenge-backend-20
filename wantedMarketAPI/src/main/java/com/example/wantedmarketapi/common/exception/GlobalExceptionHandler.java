package com.example.wantedmarketapi.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * http status : 500 AND result : FAIL
     * 시스템 예외 상황, 집중 모니터링 대상
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class, RuntimeException.class})
    public CommonResponse onException(Exception e) {
        log.error("Handler in Exception Error Message : " + e.getMessage(), e);
        return CommonResponse.fail(ErrorCode.SERVICE_UNAVAILABLE);
    }

    /**
     * http status : 200 AND result : FAIL
     * 비즈니스 로직 처리 에러, 시스템 상에서는 이슈가 없는 경우
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(WantedMarketApiException.class)
    public CommonResponse onWantedMarketApiException(WantedMarketApiException e) {
        log.error("Handler in WantedMarketApiException Error Message : " + e.getMessage(), e);
        return CommonResponse.fail(e.getMessage(), e.getErrorCode().getErrorMsg());
    }

    /**
     * http status : 400 AND result : FAIL
     * request parameter 에러
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse onInvalidParamException(MethodArgumentNotValidException e) {
        log.error("Handler in InvalidParamException Error Message : " + e.getMessage(), e);
        BindingResult bindingResult = e.getBindingResult();
        FieldError fe = bindingResult.getFieldError();
        if (fe != null) {
            String message = "Request Error" + " " + fe.getField() + "=" + fe.getRejectedValue() + " (" + fe.getDefaultMessage() + ")";
            return CommonResponse.fail(message, ErrorCode.INVALID_PARAMETER.name());
        } else {
            return CommonResponse.fail(ErrorCode.INVALID_PARAMETER.getErrorMsg(), ErrorCode.INVALID_PARAMETER.name());
        }

    }
}
