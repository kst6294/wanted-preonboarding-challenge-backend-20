package wanted.market.product

import jakarta.persistence.*
import wanted.market.member.Member


@Entity
class Product(
    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var price: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = -1
){
    var productStatus: ProductStatus = ProductStatus.SALE
}