package wanted.market.product.dto

import wanted.market.member.entity.Member
import wanted.market.order.entity.Order
import wanted.market.product.entity.Product

data class ProductDetailResponse(val name: String,
                           val price: Int,
                           val productStatus: String,
                           val orderHistory: OrderHistory
){
    companion object {
        fun from(product: Product, order: Order?, seller: Member): ProductDetailResponse {
            if (order != null) {
                return ProductDetailResponse(
                    product.priceName,
                    product.price,
                    product.productStatus.value,
                    OrderHistory.from(order, seller))
            }
            return ProductDetailResponse(
                product.priceName,
                product.price,
                product.productStatus.value,
                OrderHistory())

        }
    }
}

