package wanted.market.api.order.domain.entity

import jakarta.persistence.*
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.GenerationType.IDENTITY
import org.hibernate.annotations.DynamicUpdate
import wanted.market.api.common.BaseTimeEntity
import wanted.market.api.product.domain.entity.Product

@Entity
@DynamicUpdate
class OrderItem(

    val productName: String,

    val price: Long,

    val quantity: Int,

    @ManyToOne
    @JoinColumn(name = "product_id")
    val product: Product,

    @ManyToOne(fetch = LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "order_id")
    val orders: Order,

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "order_item_id")
    val id: Long? = null
) : BaseTimeEntity<OrderItem, Long>() {

    companion object {
        fun create(product: Product, quantity: Int, order: Order): OrderItem {
            return OrderItem(
                productName = product.name,
                price = product.price,
                quantity = quantity,
                product = product,
                orders = order
            )
        }
    }
}