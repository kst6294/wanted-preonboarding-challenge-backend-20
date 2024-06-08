package com.api.jellomarket.controller

import com.api.jellomarket.config.path.*
import com.api.jellomarket.dto.user.UserSignInRequestDTO
import com.api.jellomarket.dto.user.UserSignUpRequestDTO
import com.api.jellomarket.service.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    val userService: UserService
) {
    @PostMapping(USER_SIGN_UP)
    fun saveUser(@Validated @RequestBody request: UserSignUpRequestDTO): ResponseEntity<String> {
        val savedUser = userService.saveUser(request)
        val welcomeMessage = "Welcome to Jello Market, ${savedUser.name}!"
        return ResponseEntity.ok(welcomeMessage)
    }

    @PostMapping(USER_SIGN_IN)
    fun signIn(@Validated @RequestBody request: UserSignInRequestDTO): ResponseEntity<String> {
        val savedUser = userService.signInUser(request)
        val welcomeMessage = "Welcome to Jello Market, ${savedUser.name}!"
        return ResponseEntity.ok(welcomeMessage)
    }
}