package wanted.preonboard.market.domain.Product;

import lombok.Getter;

@Getter
public enum ProductState {
    ON_SALE("판매"), RESERVED("예약중"), COMPLETED("완료");

    private final String state;

    ProductState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return state;
    }
}
