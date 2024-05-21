package wanted.market.api.member.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id

@Entity
class Member(
    val userId: String,

    val password: String,

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    val id: Long? = null
) {
    companion object {
        fun register(userId: String, password: String): Member {
            return Member(userId, password)
        }
    }
}