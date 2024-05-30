package com.example.wanted.repository;

import com.example.wanted.WantedApplication;
import com.example.wanted.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {


    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("UserRepository Test")
    void registerName() {
        User user = User.builder().email("test@naver.com").password("11111111").name("테스트").role("ROLE_USER").build();

        User result = userRepository.save(user);

        Assertions.assertThat(result.getName()).isEqualTo(user.getName());


    }

}