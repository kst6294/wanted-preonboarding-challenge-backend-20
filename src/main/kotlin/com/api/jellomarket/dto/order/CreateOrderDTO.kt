package com.api.jellomarket.dto.order

import com.api.jellomarket.enums.order.OrderStatus
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class CreateOrderDTO(
    val userId: Long,
    val productId: Long,
    val quantity: Int,
    val price: Int,
    val status: OrderStatus,
    val totalAmount: Float,
    val createdAt: String,
    val updatedAt: String?
) {
    constructor(
        userId: Long,
        productId: Long,
        quantity: Int,
        price: Int,
        totalAmount: Float,
    ) : this(
        userId,
        productId,
        quantity,
        price,
        OrderStatus.NEW_ORDER,
        totalAmount,
        Instant.now().atZone(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
        null
    )
}
