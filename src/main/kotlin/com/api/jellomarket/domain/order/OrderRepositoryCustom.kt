package com.api.jellomarket.domain.order

import com.api.jellomarket.enums.order.OrderStatus

interface OrderRepositoryCustom {
    fun countByOrderStatus(orderStatus: OrderStatus): Long
}