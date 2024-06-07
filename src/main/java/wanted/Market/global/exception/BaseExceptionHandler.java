package wanted.Market.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import wanted.Market.global.common.ResponseDto;
import wanted.Market.global.exception.ErrorCode.ErrorCode;

@RestControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ResponseDto<?>> appExceptionHandler(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        ResponseDto<?> response = ResponseDto.fail(errorCode.getHttpStatus().value(), errorCode.getMessage() + " " + e.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
    }

}