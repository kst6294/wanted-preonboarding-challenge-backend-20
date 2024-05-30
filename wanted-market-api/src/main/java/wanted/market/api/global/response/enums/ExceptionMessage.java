package wanted.market.api.global.response.enums;

public enum ExceptionMessage {
    IS_NOT_USER("회원이 아닙니다."),
    IS_NOT_EXIST("존재하지 않는 상품입니다."),
    CANNOT_NEGATIVE("해당 값은 음수가 될 수 없습니다."),
    IS_NOT_OWNER("본인의 상품이 아닙니다."),
    PRICE_HAS_CHANGED("가격 정보가 변경되었습니다."),
    IS_NOT_ON_SALE("현재 판매중인 상품이 아닙니다."),
    IS_NOT_RESERVED("현재 예약중인 상품이 아닙니다."),

    CANNOT_ORDER_OWN_PRODUCT("본인의 상품을 주문할 수 없습니다."),
    HAS_SOLD_OUT("상품 재고가 부족합니다.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getName(){
        return message;
    }

}
