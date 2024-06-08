package com.api.jellomarket.service.user

import com.api.jellomarket.domain.user.User
import com.api.jellomarket.domain.user.UserRepository
import com.api.jellomarket.dto.user.UserSignInRequestDTO
import com.api.jellomarket.dto.user.UserSignUpRequestDTO
import com.api.jellomarket.validation.UserValidation
import org.springframework.stereotype.Service

@Service
class UserService(
    val userRepository: UserRepository
) {
    fun saveUser(request: UserSignUpRequestDTO): User {
        UserValidation().validateSignUpRequest(request)
        // UserSignUpRequestDTO 로 User 객체 만들기
        val savedUser = userRepository.save(
            User(
                email = request.email,
                password = request.password,
                name = request.name,
                phoneNumber = request.phoneNumber
            )
        )
        return savedUser
    }

    fun signInUser(request: UserSignInRequestDTO): User {
        val user = userRepository.findByEmail(request.email)
        val validUser = UserValidation().validateValidUser(user)
        return validUser
    }
}