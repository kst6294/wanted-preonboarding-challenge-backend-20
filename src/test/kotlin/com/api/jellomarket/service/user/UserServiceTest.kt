package com.api.jellomarket.service.user

import com.api.jellomarket.domain.user.UserRepository
import com.api.jellomarket.dto.user.UserSignUpRequestDTO
import jakarta.transaction.Transactional
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@Transactional
class UserServiceTest @Autowired constructor(
    private val userService: UserService,
    private val userRepository: UserRepository
) {

    @AfterEach
    fun clean() {
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("유저 저장이 정상 동작한다")
    fun saveUserTest() {
        // given
        val UserSignUpRequestDTO = UserSignUpRequestDTO(
            email = "abc@naver.com",
            password = "1234",
            name = "test",
            phoneNumber = "010-1234"
        )

        // when
        val savedUser = userService.saveUser(UserSignUpRequestDTO)

        // then
        assertEquals(savedUser.email, UserSignUpRequestDTO.email)
        // 총 유저수 1인지 확인
        assertEquals(userRepository.findAll().size, 1)
    }
}