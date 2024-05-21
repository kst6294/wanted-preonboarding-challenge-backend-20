package wanted.market.api.common

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import wanted.market.api.common.security.ErrorResponse
import wanted.market.api.log

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = [Exception::class])
    protected fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        log.error("handleException => {}", e.message)
        val response = ErrorResponse.of(e.message!!)
        return ResponseEntity<ErrorResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(value = [IllegalArgumentException::class])
    protected fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        log.error("handleIllegalArgumentException => {}", e.message)
        val response = ErrorResponse.of(e.message!!)
        return ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST)
    }
}