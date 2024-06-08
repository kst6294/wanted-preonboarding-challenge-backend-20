package com.api.jellomarket.config.exception

import com.api.jellomarket.config.ResponseCustom
import com.api.jellomarket.enums.error.ErrorCodeCustom
import com.api.jellomarket.exception.BusinessException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionAdvice {

    // 비즈니스 로직 오류 처리
    @ExceptionHandler(value = [BusinessException::class])
    fun handleException(e: BusinessException): ResponseEntity<ResponseCustom.Error> {
        val detailError = ResponseCustom.ErrorDetail.of(e.errorCode)
        detailError.systemMessage = e.message ?: ErrorCodeCustom.CALL_ADMIN.message
        return ResponseEntity.status(e.errorCode.httpStatus).body(ResponseCustom.Error(errorDetail=detailError))
    }

    // 미처리 오류
    @ExceptionHandler(value = [Exception::class])
    fun handleException(e: Exception): ResponseEntity<ResponseCustom.Error> {
        return ResponseEntity.status(500).body(ResponseCustom.Error(e.message ?: ErrorCodeCustom.CALL_ADMIN.message))
    }
}