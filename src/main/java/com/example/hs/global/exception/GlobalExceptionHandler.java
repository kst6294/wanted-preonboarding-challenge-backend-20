package com.example.hs.global.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
    log.error("{} is occurred.", e.getErrorMessage());
    return new ResponseEntity<>(new ErrorResponse(e.getErrorCode(), e.getErrorMessage()),
        HttpStatus.valueOf(e.getErrorCode()));
  }

  @ExceptionHandler(RedisConnectionFailureException.class)
  public ResponseEntity<ErrorResponse> handleRedisConnectionFailureException(RedisConnectionFailureException e) {
    log.error("RedisConnectionFailureException is occurred.", e);
    return new ResponseEntity<>(new ErrorResponse(ErrorCode.REDIS_SERVER_ERROR.getHttpCode(), ErrorCode.REDIS_SERVER_ERROR.getDescription()),
        HttpStatus.valueOf(ErrorCode.REDIS_SERVER_ERROR.getHttpCode()));
  }
  @ExceptionHandler(NullPointerException.class)
  public ResponseEntity<ErrorResponse> handleNullPointException(NullPointerException e) {
    log.error("NullPointerException is occurred.", e);
    return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR.getHttpCode(), ErrorCode.INTERNAL_SERVER_ERROR.getDescription()),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    log.error("MethodArgumentNotValidException is occurred.", e);
    String totalErrorMessage = e.getMessage();
    String unitErrorMessage = totalErrorMessage.split("; ")[5];
    return new ResponseEntity<>(new ErrorResponse(ErrorCode.INVALID_REQUEST.getHttpCode(), unitErrorMessage.substring(17, unitErrorMessage.indexOf("]"))),
        HttpStatus.valueOf(e.getStatusCode().value()));
  }

  @ExceptionHandler(MissingRequestHeaderException.class)
  public ResponseEntity<ErrorResponse> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
    log.error("{} is occurred.", e.toString());
    return new ResponseEntity<>(new ErrorResponse(400, "요청 헤더가 비어있습니다."),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(JwtException.class)
  public ResponseEntity<ErrorResponse> handleJwtException(JwtException e) {
    log.error("{} is occurred. ", e.getErrorMessage());
    return new ResponseEntity<>(new ErrorResponse(e.getErrorCode(), e.getErrorMessage()),
        HttpStatus.valueOf(e.getErrorCode()));
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException e) {
    log.error("{} is occurred.", e.toString());
    return new ResponseEntity<>(new ErrorResponse(400, "비밀번호 또는 아이디를 확인해주세요."),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InternalAuthenticationServiceException.class)
  public ResponseEntity<ErrorResponse> handleInternalAuthenticationServiceException(InternalAuthenticationServiceException e) {
    log.error("{} is occurred.", e.toString());
    return new ResponseEntity<>(new ErrorResponse(400, "아이디 또는 비밀번호를 확인해주세요."),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception e) {
    log.error("Exception is occurred.", e);
    return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR.getHttpCode(), ErrorCode.INTERNAL_SERVER_ERROR.getDescription()),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
