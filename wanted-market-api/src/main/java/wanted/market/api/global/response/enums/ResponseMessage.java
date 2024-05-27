package wanted.market.api.global.response.enums;

public enum ResponseMessage {
    COMPLETENESS("작업이 완료되었습니다.");

    private String message;
    ResponseMessage(String message){
        this.message = message;
    }

}
