package com.api.jellomarket.dto.product

import com.api.jellomarket.enums.product.ProductState

data class ProductListDTO(
    val id: Long,
    val name: String,
    val price: Int,
    val state: ProductState,
    val stock: Int,
    val imageUrl: String = "",
    val sellerId: Long,
    val createdAt: String,
)
