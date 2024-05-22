package wanted.market.api.member.domain.entity

import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY
import wanted.market.api.common.BaseTimeEntity
import wanted.market.api.product.domain.entity.Product

@Entity
class Member(
    val userId: String,

    val password: String,

    @OneToMany(mappedBy = "seller")
    val products: MutableList<Product> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    val id: Long? = null
) : BaseTimeEntity<Member, Long>() {

    companion object {

        fun register(userId: String, password: String): Member {
            return Member(userId, password)
        }
    }
}