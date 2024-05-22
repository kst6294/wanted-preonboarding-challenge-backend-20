package wanted.market.member

import jakarta.persistence.*

@Entity
@Table(name = "users")
class Member(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = -1,

        var name: String,

        @Column(unique = true)
        var email: String)