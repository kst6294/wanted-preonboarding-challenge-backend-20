package com.example.wanted.model.user;

import com.example.wanted.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class UserTest {

    @Test
    @DisplayName("회원 생성 테스트")
    void registerUserTest() {
        User user = User.builder().u_id(1L).email("test@naver.com").password("11111111").name("홍길동").role("ROLE_USER").build();
        Assertions.assertThat(user.getU_id()).isEqualTo(1L);
        Assertions.assertThat(user.getEmail()).isEqualTo("test@naver.com");
        Assertions.assertThat(user.getPassword()).isEqualTo("11111111");
        Assertions.assertThat(user.getName()).isEqualTo("홍길동");
        Assertions.assertThat(user.getRole()).isEqualTo("ROLE_USER");
    }
}