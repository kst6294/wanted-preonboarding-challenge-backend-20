package com.api.jellomarket.domain.order

import com.api.jellomarket.enums.order.OrderStatus
import jakarta.persistence.*

@Entity
@Table(name = "jello_orders")
class Order(
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val orderId: Long? = null,

    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "product_id")
    val productId: Long,

    val quantity: Int,
    val price: Int,
    val status: OrderStatus,
    val totalAmount: Float,

    @Column(name = "created_at")
    val createdAt: String,

    @Column(name = "updated_at")
    val updatedAt: String? = null
) {
}