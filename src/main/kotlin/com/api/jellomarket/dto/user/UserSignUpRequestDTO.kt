package com.api.jellomarket.dto.user

import org.jetbrains.annotations.NotNull

data class UserSignUpRequestDTO(
    @NotNull

    val email: String,

    @NotNull
    val password: String,

    @NotNull
    val name: String,

    @NotNull
    val phoneNumber: String,
)
