package wanted.market.api.product.domain.entity

import jakarta.persistence.*
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.GenerationType.IDENTITY
import wanted.market.api.product.domain.dto.`in`.CommandRegisterProduct

@Entity
class Product(
    val name: String,

    val price: Long,

    @Enumerated(STRING)
    val status: ProductStatus,

    var quantity: Int,

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "product_id")
    val id: Long? = null
) {
    fun purchase(quantity: Int) {
        if (this.quantity < quantity) {
            throw IllegalArgumentException("상품의 재고가 부족합니다.")
        }
        this.quantity -= quantity
    }

    companion object {
        fun register(request: CommandRegisterProduct) : Product {
            return Product(
                name = request.name,
                price = request.price,
                status = request.status,
                quantity = request.stockQuantity
            )
        }
    }
}