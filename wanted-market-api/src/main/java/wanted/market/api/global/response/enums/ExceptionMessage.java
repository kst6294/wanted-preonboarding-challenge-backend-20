package wanted.market.api.global.response.enums;

public enum ExceptionMessage {
    ISNOTUSER("회원이 아닙니다."),
    ISNOTEXIST("존재하지 않는 상품입니다."),
    CANNOTNEGATIVE("해당 값은 음수가 될 수 없습니다."),
    ISNOTOWNER("본인의 상품이 아닙니다."),
    HASCHANGED("가격 정보가 변경되었습니다.");
    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getName(){
        return message;
    }

}
