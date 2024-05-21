package wanted.market.api.order.domain.entity

enum class OrderStatus(
    val description: String
) {
    NONE("미선택"),
    SALEAPPROVAL("판매승인"),
    PURCHASECONFIRM("구매확정")
}