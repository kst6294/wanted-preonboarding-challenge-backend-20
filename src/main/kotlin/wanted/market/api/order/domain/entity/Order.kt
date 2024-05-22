package wanted.market.api.order.domain.entity

import jakarta.persistence.*
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.GenerationType.IDENTITY
import org.springframework.boot.context.properties.bind.DefaultValue
import wanted.market.api.common.BaseTimeEntity
import wanted.market.api.member.domain.entity.Member
import wanted.market.api.order.domain.entity.OrderStatus.*

@Entity
class Order(

    @OneToMany(mappedBy = "orders")
    val orderItems: MutableList<OrderItem> = mutableListOf(),

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "buyer_id")
    val buyer: Member,

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "seller_id")
    val seller: Member,

    @Enumerated(STRING)
    @DefaultValue("NONE")
    var orderStatus: OrderStatus = NONE,

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "order_id")
    val id: Long? = null
) : BaseTimeEntity<Order, Long>() {

    fun approveOrder() {
        this.orderStatus = SALEAPPROVAL
    }

    fun confirmOrder() {
        this.orderStatus = PURCHASECONFIRM
    }

    companion object {
        fun create(buyer: Member, seller: Member): Order {
            return Order(
                buyer = buyer,
                seller = seller
            )
        }
    }
}