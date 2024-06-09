package wanted.market.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {
    
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증되지 않은 유저입니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "Access Token 이 만료되었습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 파라미터 타입입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "잘못된 HTTP 요청입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 메서드를 찾을 수 없습니다."),
    BAD_DTO_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 DTO 객체 바인딩 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러가 발생했습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 조회할 수 없습니다."),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "제품을 조회할 수 없습니다."),
    TRANSACTION_NOT_FOUND(HttpStatus.NOT_FOUND, "거래를 조회할 수 없습니다."),
    DATA_NOT_FOUND(HttpStatus.NOT_FOUND, "데이터를 조회할 수 없습니다."),
    DUPLICATED_DATA(HttpStatus.BAD_REQUEST, "중복된 데이터 저장 시도입니다."),
    INVALID_INPUT_DATE(HttpStatus.BAD_REQUEST, "잘못된 데이터 입력입니다.")

    ;

    private final HttpStatus status;
    private final String message;

}