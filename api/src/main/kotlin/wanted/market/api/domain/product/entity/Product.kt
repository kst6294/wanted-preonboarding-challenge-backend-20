package wanted.market.api.domain.product.entity

import jakarta.persistence.*
import wanted.market.api.global.common.BaseTimeEntity
import wanted.market.api.domain.member.entity.Member
import wanted.market.api.domain.product.entity.ProductStatus

@Entity
class Product(
    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val price: Int,

    @Enumerated(EnumType.STRING)
    val status: ProductStatus = ProductStatus.SALE,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    var seller: Member,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) : BaseTimeEntity()
