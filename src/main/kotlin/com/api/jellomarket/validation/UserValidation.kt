package com.api.jellomarket.validation

import com.api.jellomarket.domain.user.User
import com.api.jellomarket.dto.user.UserSignInRequestDTO
import com.api.jellomarket.dto.user.UserSignUpRequestDTO

class UserValidation(
) {
    fun validateSignUpRequest(requestDTO: UserSignUpRequestDTO) {
        if (requestDTO.email.isBlank()) {
            throw IllegalArgumentException("Email is required")
        }
        if (requestDTO.password.isBlank()) {
            throw IllegalArgumentException("Password is required")
        }
    }

    fun validateSignInRequest(requestDTO: UserSignInRequestDTO) {
        if (requestDTO.email.isBlank()) {
            throw IllegalArgumentException("이메일을 입력해주세요")
        }
        if (requestDTO.password.isBlank()) {
            throw IllegalArgumentException("비밀번호를 입력해주세요")
        }
    }

    fun validateValidUser(user: User?): User {
        if (user == null) {
            throw IllegalArgumentException("해당하는 이메일을 가진 유저가 없습니다.")
        }
        if (user.email.isBlank()) {
            throw IllegalArgumentException("이메일을 입력해주세요")
        }
        if (user.password.isBlank()) {
            throw IllegalArgumentException("비밀번호를 입력해주세요")
        }
        return user
    }
}