package wanted.market.product.dto

import wanted.market.member.entity.Member
import wanted.market.order.entity.Order


data class OrderHistory(val orderDateTime: String = "",
                        val orderStatus: String = "",
                        val sellerName: String = "",
                        val buyerName: String = "",
                        val orderCompleteDate: String = "") {

    companion object {
        fun from(order: Order, seller: Member): OrderHistory {
            return OrderHistory(
                order.createdDate.toString(),
                order.orderStatus.toString(),
                seller.name,
                order.buyer.name,
                order.completeDate.toString())
        }
    }
}
