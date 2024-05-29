package wanted.market.api.global.response.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import wanted.market.api.global.response.dto.response.ErrorResponseDto;
import wanted.market.api.global.response.enums.ExceptionDomain;
import wanted.market.api.global.response.exception.WantedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WantedException.class)
    public ResponseEntity<ErrorResponseDto> handleAll(WantedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseDto.builder().domain(e.getDomain()).message(e.getMessage()).build());
    }

    //temp
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDto> handleUndefined(RuntimeException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseDto.builder().domain(ExceptionDomain.UNDEFINED.getValue()).message(e.getMessage()).build());
    }
}
