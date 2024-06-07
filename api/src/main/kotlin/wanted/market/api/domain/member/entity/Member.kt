package wanted.market.api.domain.member.entity

import jakarta.persistence.*
import wanted.market.api.global.common.BaseTimeEntity

@Entity
class Member(
    val email: String,

    @Column(nullable = false, unique = true)
    val password: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) : BaseTimeEntity()
