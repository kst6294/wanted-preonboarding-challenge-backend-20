package wanted.market.domain.transcation.repository.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionStatus {

    TRADING("거래중"),
    TRADE_COMPLETE("거래완료"),
    TRADE_CANCEL("거래취소");

    private final String text;
}
