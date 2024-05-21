package wanted.market.api.order.domain.entity

import jakarta.persistence.*
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.GenerationType.IDENTITY
import wanted.market.api.common.BaseTimeEntity

@Entity
class OrderItem(

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    val orders: Order,

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "order_item_id")
    val id: Long? = null
) : BaseTimeEntity<OrderItem, Long>() {
}