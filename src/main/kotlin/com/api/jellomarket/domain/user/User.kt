package com.api.jellomarket.domain.user;

import jakarta.persistence.*

@Entity
@Table(name = "jello_user")
class User(
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long? = null,

    @Column(name = "email", columnDefinition = "CHAR(22)")
    val email: String,

    @Column(name = "password")
    val password: String,

    @Column(name = "name", columnDefinition = "CHAR(30)")
    val name: String? = null,

    @Column(name = "phone_number", columnDefinition = "CHAR(30)")
    val phoneNumber: String? = null,

) {
    override fun toString(): String {
        return "User(userId=$userId, email=$email, password=$password, name=$name)"
    }
}

