package wanted.market.product

import jakarta.persistence.*


@Entity
class Product(
    var name: String,

    var price: Int,

    var productStatus: ProductStatus = ProductStatus.SALE
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}