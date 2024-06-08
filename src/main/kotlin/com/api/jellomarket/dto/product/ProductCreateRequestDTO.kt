package com.api.jellomarket.dto.product

import com.api.jellomarket.enums.product.ProductState

data class ProductCreateRequestDTO(
    val name: String,
    val price: Int,
    val state: ProductState,
    val stock: Int,
    val description: String = "",
    val imageUrl: String,
    val category: String,
    val createdAt: String,
    val updatedAt: String = "",
    val deletedAt: String = ""
) {
    constructor(
        name: String,
        price: Int,
        state: ProductState,
        stock: Int,
        createdAt: String
    ) : this(name, price, state, stock, "", "", "", createdAt)
}
