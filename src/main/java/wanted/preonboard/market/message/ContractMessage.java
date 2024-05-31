package wanted.preonboard.market.message;

import lombok.Getter;

@Getter
public enum ContractMessage {
    ERROR("거래 등록 중 알 수 없는 오류가 발생했습니다."),
    CONTRACT_NOT_FOUND("거래을 찾을 수 없습니다."),
    CONTRACT_ALREADY_EXISTS("이미 존재하는 거래입니다."),
    CONTRACT_NOT_AUTHORIZED("거래 권한이 없습니다."),
    INVALID_CONTRACT_STATE("유효하지 않은 거래 상태입니다."),
    INVALID_CONTRACT_ID("유효하지 않은 거래 ID입니다."),
    REGISTERED_SUCCESSFULLY("거래 등록이 완료되었습니다."),
    ALREADY_APPROVED("이미 승인된 거래입니다."),
    APPROVED_SUCCESSFULLY("판매 승인이 완료되었습니다."),
    ;

    private final String message;

    ContractMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
