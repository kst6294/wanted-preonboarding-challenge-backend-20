package com.example.wanted.user.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    public void User는_UserCreate로_생성할_수_있다() {
        //given
        UserCreate userCreate = UserCreate.builder()
                .account("test@gmail.com")
                .name("홍길동")
                .password("test1234")
                .build();

        //when
        User user = User.from(userCreate);

        //then
        assertThat(user.getAccount()).isEqualTo("test@gmail.com");
        assertThat(user.getName()).isEqualTo("홍길동");
        assertThat(user.getPassword()).isEqualTo("test1234");
    }
}