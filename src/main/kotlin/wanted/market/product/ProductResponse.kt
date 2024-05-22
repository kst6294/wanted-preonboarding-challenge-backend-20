package wanted.market.product

data class ProductResponse(val name: String,
                           val price: Int,
                           val productStatus: String
){
    companion object {
        fun from(product: Product): ProductResponse {
            return ProductResponse(product.name, product.price, product.productStatus.value)
        }
    }
}

