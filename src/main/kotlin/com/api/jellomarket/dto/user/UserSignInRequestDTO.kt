package com.api.jellomarket.dto.user

import org.jetbrains.annotations.NotNull

data class UserSignInRequestDTO(
    @NotNull
    val email: String,

    @NotNull
    val password: String
)
