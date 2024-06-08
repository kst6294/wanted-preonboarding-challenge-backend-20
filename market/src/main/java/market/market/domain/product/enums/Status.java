package market.market.domain.product.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {

    Sale("SALE", "판매 중"),
    Reservation("RESERVATION", "예약 중"),
    Completion("COMPLETION", "판매 완료");

    private final String key;
    private final String title;
}
