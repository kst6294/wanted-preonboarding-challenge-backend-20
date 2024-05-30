package wanted.market.product.dto

import wanted.market.product.entity.Product

data class ProductResponse(val name: String,
                      val price: Int,
                      val productStatus: String) {

    companion object {
        fun from(product: Product): ProductResponse {
            return ProductResponse(
                product.priceName,
                product.price,
                product.productStatus.value)
        }
    }
}