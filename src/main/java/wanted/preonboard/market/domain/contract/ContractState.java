package wanted.preonboard.market.domain.contract;

import lombok.Getter;

@Getter
public enum ContractState {
    WAITING("거래대기"), COMPLETED("거래완료");

    private final String state;

    ContractState(String state) {
        this.state = state;
    }
}
