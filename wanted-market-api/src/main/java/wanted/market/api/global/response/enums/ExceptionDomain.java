package wanted.market.api.global.response.enums;

public enum ExceptionDomain {
    USER("유저"),
    PRODUCT("상품"),
    ORDER("주문"),
    PAGE("페이지"),
    UNDEFINED("UNDEFINED");

    private final String value;

    ExceptionDomain(String value){
        this.value = value;
    }
    public String getValue(){
        return this.value;
    }
}
