package com.api.jellomarket.dto.product

import com.api.jellomarket.enums.order.OrderStatus

data class ProductPurchaseResponseDTO(
    val orderId: Long,
    val status: OrderStatus,
    val orderDate: String,
)
