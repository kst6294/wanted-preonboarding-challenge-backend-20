package wanted.market.api.product.domain.dto.out

import wanted.market.api.order.domain.dto.out.RetrievePurchaseHistoryResult
import wanted.market.api.order.domain.dto.out.RetrieveReservationHistoryResult
import wanted.market.api.product.domain.entity.Product
import wanted.market.api.product.domain.entity.ProductStatus

data class RetrieveProductResult(
    val sellerId: Long,
    val id: Long,
    val name: String,
    val price: Long,
    val quantity: Int,
    val status: ProductStatus,
    val purchaseHistory: List<RetrievePurchaseHistoryResult>? = mutableListOf(),
    val sellHistory: List<RetrieveReservationHistoryResult> = mutableListOf()
) {
    companion object {
        fun from(products: Product): RetrieveProductResult {
            return RetrieveProductResult(
                sellerId = products.seller.id!!,
                id = products.id!!,
                name = products.name,
                price = products.price,
                quantity = products.quantity,
                status = products.status
            )
        }
        fun from(products: Product, purchaseHistoryResult: List<RetrievePurchaseHistoryResult>, sellHistoryResult: List<RetrieveReservationHistoryResult>): RetrieveProductResult {
            return RetrieveProductResult(
                sellerId = products.seller.id!!,
                id = products.id!!,
                name = products.name,
                price = products.price,
                quantity = products.quantity,
                status = products.status,
                purchaseHistory = purchaseHistoryResult,
                sellHistory = sellHistoryResult
            )
        }
    }
}