package wanted.market.order

import jakarta.persistence.*

@Entity
@Table(name = "orders")
class Order (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = -1
)