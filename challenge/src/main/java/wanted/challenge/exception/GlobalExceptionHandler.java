package wanted.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import wanted.challenge.exception.custom.GoodsNotFoundException;
import wanted.challenge.exception.custom.UnauthorizedAccessException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>("Service error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return new ResponseEntity<>("Invalid request: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GoodsNotFoundException.class)
    public ResponseEntity<Object> handleGoodsNotFoundException(GoodsNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>("Goods not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<Object> handleUnauthorizedAccessException(UnauthorizedAccessException ex, WebRequest request) {
        return new ResponseEntity<>("Unauthorized access: " + ex.getMessage(), HttpStatus.FORBIDDEN);
    }
}
