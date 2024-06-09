package wanted.market.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import wanted.market.global.dto.ErrorResultDto;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<ErrorResultDto> handleCustomException(RestApiException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus()).body(makeResponseBody(errorCode));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        ErrorCode errorCode = makeErrorCode(ex);
        return ResponseEntity.status(errorCode.getStatus()).body(makeResponseBody(errorCode));
    }

    private ErrorCode makeErrorCode(Exception ex) {
        if (ex instanceof IllegalArgumentException) {
            return CommonErrorCode.BAD_REQUEST;
        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
            return CommonErrorCode.METHOD_NOT_ALLOWED;
        } else if (ex instanceof NoHandlerFoundException) {
            return CommonErrorCode.NOT_FOUND;
        } else if (ex instanceof HttpMessageNotReadableException) {
            return CommonErrorCode.BAD_REQUEST;
        } else if (ex instanceof MethodArgumentNotValidException) {
            return CommonErrorCode.INVALID_INPUT_DATE;
        }
        return CommonErrorCode.INTERNAL_SERVER_ERROR;
    }

    private ErrorResultDto makeResponseBody(ErrorCode errorCode) {
        return new ErrorResultDto(errorCode.getStatus(), errorCode.getStatus().value(), errorCode.getMessage());
    }
}