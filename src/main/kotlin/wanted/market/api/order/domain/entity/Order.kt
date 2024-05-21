package wanted.market.api.order.domain.entity

import jakarta.persistence.*
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.GenerationType.IDENTITY
import wanted.market.api.common.BaseTimeEntity
import wanted.market.api.member.domain.entity.Member

@Entity
class Order(

    @OneToMany(mappedBy = "orders")
    val orderProducts: MutableList<OrderItem> = mutableListOf(),

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "buyer_id")
    val buyer: Member,

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "seller_id")
    val seller: Member,

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "order_id")
    val id: Long? = null
) : BaseTimeEntity<Order, Long>() {
}