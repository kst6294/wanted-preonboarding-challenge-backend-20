package kr.co.wanted.market.trade.enums;

import java.util.Optional;

public enum TradeState {

    // 거래 요청
    REQUEST,

    // 예약
    BOOKING,

    // 확정
    CONFIRMATION,

    // 취소
    CANCEL,
    ;

    public Optional<TradeState> next() {

        if (this == CONFIRMATION || this == CANCEL) {
            return Optional.empty();
        }

        return Optional.of(values()[ordinal() + 1]);
    }


    public Optional<TradeState> previous() {

        if (this == REQUEST) {
            return Optional.of(CANCEL);
        }

        if (this == CONFIRMATION || this == CANCEL) {
            return Optional.empty();
        }

        return Optional.of(values()[ordinal() - 1]);
    }

}
