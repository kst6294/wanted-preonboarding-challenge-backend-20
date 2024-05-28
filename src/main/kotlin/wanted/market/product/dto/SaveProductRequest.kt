package wanted.market.product.dto

data class SaveProductRequest(val name: String,
                              val memberId: Long,
                              val price: Int)
