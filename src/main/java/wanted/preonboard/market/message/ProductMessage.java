package wanted.preonboard.market.message;

import lombok.Getter;

@Getter
public enum ProductMessage {
    ERROR("제품 등록 중 알 수 없는 오류가 발생했습니다."),
    FIND_ERROR("제품 조회 중 알 수 없는 오류가 발생했습니다."),
    PRODUCT_NOT_FOUND("제품을 찾을 수 없습니다."),
    PRODUCT_ALREADY_EXISTS("이미 존재하는 제품입니다."),
    PRODUCT_ALREADY_SOLD("이미 판매된 제품입니다."),
    PRODUCT_NOT_AUTHORIZED("제품 권한이 없습니다."),
    INVALID_PRODUCT_STATE("유효하지 않은 제품 상태입니다."),
    INVALID_PRODUCT_ID("유효하지 않은 제품 ID입니다."),
    REGISTERED_SUCCESSFULLY("제품 등록이 완료되었습니다."),
    PRODUCT_ALREADY_RESERVED("이미 예약된 제품입니다."),
    UPDATED_SUCCESSFULLY("제품 수정이 완료되었습니다.");

    private final String message;

    ProductMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
