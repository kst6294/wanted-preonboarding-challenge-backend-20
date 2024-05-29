package com.wanted.market.global.common.exception.handler;

import com.wanted.market.global.common.code.ResponseCode;
import com.wanted.market.global.common.exception.DataNotFoundException;
import com.wanted.market.global.common.exception.DefaultException;
import com.wanted.market.global.common.exception.DuplicateException;
import com.wanted.market.global.common.exception.NoPermissionException;
import com.wanted.market.global.common.response.BaseResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public BaseResponse exceptionHandler(Exception e) {
        return new BaseResponse(ResponseCode.SERVER_ERROR);
    }

    @ExceptionHandler(DefaultException.class)
    public BaseResponse defaultExceptionHandler(DefaultException e) {
        return new BaseResponse(ResponseCode.SERVER_ERROR);
    }

    @ExceptionHandler(DuplicateException.class)
    public BaseResponse duplicateExceptionHandler(DuplicateException e) {
        return new BaseResponse(ResponseCode.BAD_REQUEST);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public BaseResponse dataNotFoundExceptionHandler(DataNotFoundException e) {
        return new BaseResponse(ResponseCode.BAD_REQUEST);
    }

    @ExceptionHandler(NoPermissionException.class)
    public BaseResponse dataNotFoundExceptionHandler(NoPermissionException e) {
        return new BaseResponse(ResponseCode.NO_PERMISSION);
    }
}
