package com.api.jellomarket.dto.product

data class ProductDetailDTO(
    val id: Long,
    val name: String,
    val price: Int,
    val state: String,
    val stock: Int,
    val description: String,
    val imageUrl: String,
    val sellerId: Long,
    val createdAt: String,
    val updatedAt: String,
)
