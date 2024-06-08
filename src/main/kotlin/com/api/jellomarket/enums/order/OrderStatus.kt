package com.api.jellomarket.enums.order

enum class OrderStatus {
    ON_RESERVED,
    NEW_ORDER,
    WAITING_DISPATCH,
    DELIVERING,
    DELIVERED,
    PURCHASE_DECIDED,
    CANCELED
}