package wanted.market.api.domain.product_order.entity

import jakarta.persistence.*
import wanted.market.api.global.common.BaseTimeEntity
import wanted.market.api.domain.member.entity.Member
import wanted.market.api.domain.product.entity.Product
import wanted.market.api.domain.product_order.entity.ProductOrderStatus

@Entity
class ProductOrder(
    @Enumerated(EnumType.STRING)
    val status: ProductOrderStatus = ProductOrderStatus.PENDING,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    var seller: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    var buyer: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    var product: Product,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) : BaseTimeEntity()
