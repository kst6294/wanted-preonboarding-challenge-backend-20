package wanted.market.api.global.response.enums;

public enum ExceptionMessage {
    ISNOTUSER("회원이 아닙니다.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getName(){
        return message;
    }

}
