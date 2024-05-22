package wanted.market.member

import jakarta.persistence.*

@Entity
@Table(name = "users")
class Member(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = -1, //TODO
        var name: String,

        @Column(unique = true)
        var email: String
) {

}


