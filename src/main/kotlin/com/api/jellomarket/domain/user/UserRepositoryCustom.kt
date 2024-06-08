package com.api.jellomarket.domain.user

interface UserRepositoryCustom {

    fun findByEmail(email: String): User?
}