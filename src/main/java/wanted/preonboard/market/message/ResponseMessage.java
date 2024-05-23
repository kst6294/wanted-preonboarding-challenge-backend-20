package wanted.preonboard.market.message;

import lombok.Getter;

@Getter
public enum ResponseMessage {
    STATUS("status"),
    MESSAGE("message"),
    DATA("data"),
    SUCCESS("success"),
    FAILED("failed"),
    ERROR("error");

    private final String key;

    ResponseMessage(String key) {
        this.key = key;
    }

}
