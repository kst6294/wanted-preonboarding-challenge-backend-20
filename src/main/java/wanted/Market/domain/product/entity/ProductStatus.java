package wanted.Market.domain.product.entity;

public enum ProductStatus {
    SALE("판매중"),
    RESERVED("예약중"),
    COMPLETED("완료");

    private final String description;

    ProductStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}