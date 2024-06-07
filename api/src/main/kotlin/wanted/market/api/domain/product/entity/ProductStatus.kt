package wanted.market.api.domain.product.entity

// TODO: 추후 converter 적용해서 int로 저장
enum class ProductStatus(val value: String) {
    SALE("sale"),
    RESERVED("reserved"),
    SOLD("sold")
}
