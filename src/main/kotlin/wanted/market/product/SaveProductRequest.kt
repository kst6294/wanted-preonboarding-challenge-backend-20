package wanted.market.product

data class SaveProductRequest(val name: String,
                              val memberId: Long,
                              val price: Int)
