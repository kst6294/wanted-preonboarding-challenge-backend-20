package wanted.market.api.global.response.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import wanted.market.api.global.response.dto.response.MessageResponseDto;
import wanted.market.api.global.response.exception.WantedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WantedException.class)
    public ResponseEntity<MessageResponseDto> handleAll(WantedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(MessageResponseDto.builder().message(e.getMessage()).build());
    }

    //temp
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<MessageResponseDto> handleAll(RuntimeException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(MessageResponseDto.builder().message(e.getMessage()).build());
    }
}
