package kr.co.wanted.market.common.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /* common */
    SERVER_ERROR("관리자에게 문의하세요.", INTERNAL_SERVER_ERROR),
    TOKEN_INVALID("토큰검증 실패", UNAUTHORIZED),
    NO_PERMISSION("권한없음", FORBIDDEN),
    NOT_VALIDATED("입력값 검증 실패", BAD_REQUEST),


    /* member */
    MEMBER_ID_DUPLICATED("중복된 ID 입니다.", BAD_REQUEST),
    MEMBER_LOGIN_FAIL("로그인 정보가 틀립니다.", BAD_REQUEST),
    MEMBER_NOT_FOUND("회원정보를 찾을 수 없습니다.", NOT_FOUND),


    /* product */
    PRODUCT_NOT_FOUND("상품정보를 찾을 수 없습니다.", NOT_FOUND),
    PRODUCT_NOT_SALE("구매할 수 없는 상품입니다.", BAD_REQUEST),
    PRODUCT_SOLD_OUT("품절입니다.", BAD_REQUEST),

    /* trade */
    TRADE_ALREADY_EXISTED("상품당 거래는 한번만 가능합니다.", BAD_REQUEST),
    TRADE_NOT_PROCESSED("거래 생성이 불가능합니다.", BAD_REQUEST),
    TRADE_STATE_CAN_NOT_CHANGED("상태를 변경할 수 없습니다.", BAD_REQUEST),
    TRADE_NOT_FOUND("거래정보를 찾을 수 없습니다.", NOT_FOUND),

    ;
    private final String message;

    private final HttpStatus httpStatus;

}
