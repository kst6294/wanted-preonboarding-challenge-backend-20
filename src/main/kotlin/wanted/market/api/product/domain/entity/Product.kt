package wanted.market.api.product.domain.entity

import jakarta.persistence.*
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.GenerationType.IDENTITY
import org.hibernate.annotations.DynamicUpdate
import wanted.market.api.common.BaseTimeEntity
import wanted.market.api.member.domain.entity.Member
import wanted.market.api.product.domain.dto.`in`.CommandRegisterProduct

@Entity
@DynamicUpdate
class Product(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    val seller: Member,

    val name: String,

    val price: Long,

    @Enumerated(STRING)
    var status: ProductStatus,

    var quantity: Int,

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "product_id")
    val id: Long? = null
) : BaseTimeEntity<Product, Long>() {

    fun purchase(quantity: Int) {
        if (this.status != ProductStatus.SALE) {
            throw IllegalArgumentException("판매중인 상품만 구매할 수 있습니다.")
        }

        this.quantity -= quantity

        when {
            quantity == 0 -> this.status = ProductStatus.RESERVATION
            quantity < 0 -> throw IllegalArgumentException("상품의 재고가 부족합니다.")
        }
    }

    companion object {
        fun register(request: CommandRegisterProduct, member: Member) : Product {
            return Product(
                name = request.name,
                price = request.price,
                status = request.status,
                quantity = request.quantity,
                seller = member
            )
        }
    }
}