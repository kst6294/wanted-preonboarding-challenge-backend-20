package wanted.preonboard.market.domain.contract;

import lombok.Getter;

@Getter
public enum ContractState {
    WAITING_APPROVAL("거래대기"), APPROVED("거래승인");

    private final String state;

    ContractState(String state) {
        this.state = state;
    }
}
