package wanted.market.product.entity

import jakarta.persistence.*
import wanted.market.common.BaseEntity
import wanted.market.member.entity.Member


@Entity
class Product(
    @Column(nullable = false)
    var priceName: String,

    @Column(nullable = false)
    var price: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var seller: Member,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = -1

) : BaseEntity() {
    @Enumerated(EnumType.STRING)
    var productStatus: ProductStatus = ProductStatus.SALE
}