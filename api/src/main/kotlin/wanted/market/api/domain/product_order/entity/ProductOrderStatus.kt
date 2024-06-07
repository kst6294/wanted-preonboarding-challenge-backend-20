package wanted.market.api.domain.product_order.entity

// TODO: 추후 converter 적용해서 int로 저장
enum class ProductOrderStatus(val value: String) {
    PENDING("pending"),
    APPROVED("approved"),
    CONFIRMED("confirmed")
    
}