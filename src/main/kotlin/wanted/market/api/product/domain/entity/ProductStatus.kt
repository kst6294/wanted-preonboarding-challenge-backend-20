package wanted.market.api.product.domain.entity

enum class ProductStatus(
    val description: String
) {
    SALE("판매중"),
    RESERVATION("예약중"),
    SOLD_OUT("완료")
}