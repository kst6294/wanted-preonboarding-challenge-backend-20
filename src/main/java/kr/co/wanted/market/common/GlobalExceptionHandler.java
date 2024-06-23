package kr.co.wanted.market.common;

import com.auth0.jwt.exceptions.JWTVerificationException;
import kr.co.wanted.market.common.global.dto.ErrorResponse;
import kr.co.wanted.market.common.global.error.BizException;
import kr.co.wanted.market.common.global.utils.ContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static kr.co.wanted.market.common.global.enums.ErrorCode.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logging(e);

        Map<String, String> validations = new HashMap<>();

        e.getFieldErrors()
                .forEach(error -> validations.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity
                .status(NOT_VALIDATED.getHttpStatus())
                .body(ErrorResponse.of(NOT_VALIDATED.name(), NOT_VALIDATED.getMessage(), validations));
    }


    /**
     * 인증실패
     */
    @ExceptionHandler({AuthenticationException.class, JWTVerificationException.class})
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(Exception e) {
        logging(e);

        return ResponseEntity
                .status(TOKEN_INVALID.getHttpStatus())
                .body(ErrorResponse.of(TOKEN_INVALID.name(), TOKEN_INVALID.getMessage(), null));
    }


    /**
     * 권한없음
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        logging(e);

        return ResponseEntity
                .status(NO_PERMISSION.getHttpStatus())
                .body(ErrorResponse.of(NO_PERMISSION.name(), NO_PERMISSION.getMessage(), null));
    }


    /**
     * 서비스 예외 핸들링
     */
    @ExceptionHandler(BizException.class)
    protected ResponseEntity<ErrorResponse> handleBizException(BizException e) {
        logging(e);

        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ErrorResponse.of(e.getCode(), e.getMessage(), null));
    }


    /**
     * 미처리 예외 핸들링
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        logging(e);

        return ResponseEntity
                .internalServerError()
                .body(ErrorResponse.of(SERVER_ERROR.name(), SERVER_ERROR.getMessage(), null));
    }


    private static void logging(Exception e) {

        final String memberId = ContextUtil.getMemberId()
                .map(String::valueOf)
                .orElse("X");

        log.warn("[{}] | 사용자: [{}]", e.getClass().getSimpleName(), memberId, e);
    }
}
