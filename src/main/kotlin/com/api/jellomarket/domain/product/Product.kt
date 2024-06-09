package com.api.jellomarket.domain.product

import com.api.jellomarket.enums.product.ProductState
import jakarta.persistence.*

@Entity
@Table(name = "jello_product")
class Product (
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var productId: Long? = null,

    var name: String,

    var price: Int,

    var state: ProductState,

    var stock: Int,

    var description: String? = null,

    @Column(name = "image_url")
    var imageUrl: String? = null,

    @Column(name = "seller_id")
    var sellerId: Long? = null,

    @Column(name = "created_at")
    var createdAt: String? = null,

    @Column(name = "updated_at")
    var updatedAt: String? = null,

    @Column(name = "deleted_at")
    var deletedAt: String? = null
) {

}