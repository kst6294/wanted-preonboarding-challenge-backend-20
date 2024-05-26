package wanted.market.member

import jakarta.persistence.*

@Entity
@Table(name = "members")
class Member(
        var name: String,

        @Column(nullable = false, unique = true)
        var email: String,

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = -1

)

