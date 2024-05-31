package com.example.wanted.user.service;

import com.example.wanted.mock.FakeUserRepository;
import com.example.wanted.module.exception.ResourceAlreadyException;
import com.example.wanted.user.domain.User;
import com.example.wanted.user.domain.UserCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserServiceTest {
    @Autowired
    private UserService userService;

    @BeforeEach
    void init() {
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        this.userService = UserService.builder()
                .userRepository(fakeUserRepository)
                .build();
    }

    @Test
    void User는_UserCreate_객체로_생성할_수_있다(){
        //given
        UserCreate userCreate = UserCreate.builder()
                .name("홍길동")
                .account("test01@gmail.com")
                .password("test123")
                .build();

        //when
        User user = userService.create(userCreate);

        //then
        assertThat(user.getId()).isNotNull();
        assertThat(user.getName()).isEqualTo("홍길동");
        assertThat(user.getAccount()).isEqualTo("test01@gmail.com");
        assertThat(user.getPassword()).isEqualTo("test123");
    }

    @Test
    void Account가_이미_존재하면_예외가_발생한다(){
        //given
        UserCreate userCreate = UserCreate.builder()
                .name("홍길동")
                .account("test01@gmail.com")
                .password("test123")
                .build();
        User user1 = userService.create(userCreate);

        //when
        //then
        assertThatThrownBy(() ->
                userService.create(userCreate)
        ).isInstanceOf(ResourceAlreadyException.class);
    }

}