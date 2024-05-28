package wanted.market.order.entity

import jakarta.persistence.*
import wanted.market.common.BaseEntity
import wanted.market.member.entity.Member
import wanted.market.product.entity.Product

@Entity
@Table(name = "orders")
class Order (
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumer_id")
    var buyer: Member,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    val product: Product,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = -1

) : BaseEntity() {
    @Enumerated(EnumType.STRING)
    var orderStatus: OrderStatus? = OrderStatus.PENDING
}