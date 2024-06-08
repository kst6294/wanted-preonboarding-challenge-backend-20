package wanted.market.domain.product.repository.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationStatus {

    SALE("판매중"),
    RESERVATION("예약중"),
    COMPLETED("완료");

    private final String text;
}
