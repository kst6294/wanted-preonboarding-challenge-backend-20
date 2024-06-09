package com.example.wanted.user.service;

import com.example.wanted.mock.FakeJwtUtil;
import com.example.wanted.mock.FakeUserRepository;
import com.example.wanted.module.exception.InvalidCredentialsException;
import com.example.wanted.module.exception.ResourceAlreadyException;
import com.example.wanted.module.exception.ResourceNotFoundException;
import com.example.wanted.user.domain.User;
import com.example.wanted.user.domain.UserCreate;
import com.example.wanted.user.domain.UserLogin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.naming.AuthenticationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserServiceTest {
    @Autowired
    private UserService userService;

    @BeforeEach
    void init() {
        FakeJwtUtil fakeJwtUtil = new FakeJwtUtil();
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        this.userService = UserService.builder()
                .jwtUtil(fakeJwtUtil)
                .userRepository(fakeUserRepository)
                .build();
    }

    @Test
    void User는_UserCreate_객체로_생성할_수_있다() throws AuthenticationException {
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
    void Account가_이미_존재하면_예외가_발생한다() throws AuthenticationException {
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

    @Test
    void UserLogin으로_로그인하면_토큰이_반환된다() throws AuthenticationException {
        //given
        User user1 = userService.create(UserCreate.builder()
                .name("홍길동")
                .account("test01@gmail.com")
                .password("test123")
                .build());

        UserLogin userLogin = UserLogin.builder()
                .account("test01@gmail.com")
                .password("test123")
                .build();

        //when
        String result = userService.login(userLogin);

        //then
        assertThat(result).isEqualTo("aasdfasdfasdfasdfasdfasdfasdf");
    }

    @Test
    void 존재하지_않는_Account면_예외가_발생한다(){
        //given
        UserLogin userLogin = UserLogin.builder()
                .account("test01@gmail.com")
                .password("test123")
                .build();

        //when
        //then
        assertThatThrownBy(() ->
                userService.login(userLogin)
        ).isInstanceOf(InvalidCredentialsException.class);
    }
    @Test
    void password가_틀리면_예외가_발생한다() throws AuthenticationException {
        //given
        User user1 = userService.create(UserCreate.builder()
                .name("홍길동")
                .account("test01@gmail.com")
                .password("test123")
                .build());

        UserLogin userLogin = UserLogin.builder()
                .account("test01@gmail.com")
                .password("test1234")
                .build();

        //when
        //then
        assertThatThrownBy(() ->
                userService.login(userLogin)
        ).isInstanceOf(InvalidCredentialsException.class);
    }

    @Test
    void UserId로_유저_정보를_가져올_수_있다() throws AuthenticationException {
        //given
        User user1 = userService.create(UserCreate.builder()
                .name("홍길동")
                .account("test01@gmail.com")
                .password("test123")
                .build());

        //when
        User user = userService.getById(user1.getId());

        //then
        assertThat(user.getId()).isNotNull();
        assertThat(user.getName()).isEqualTo("홍길동");
        assertThat(user.getAccount()).isEqualTo("test01@gmail.com");
        assertThat(user.getPassword()).isEqualTo("test123");
    }

    @Test
    void 없는_UserId로_조회_시_예외가_발생한다() throws AuthenticationException {
        //given
        User user1 = userService.create(UserCreate.builder()
                .name("홍길동")
                .account("test01@gmail.com")
                .password("test123")
                .build());

        //when
        //then
        assertThatThrownBy(() ->
                userService.getById(user1.getId() + 1)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

}