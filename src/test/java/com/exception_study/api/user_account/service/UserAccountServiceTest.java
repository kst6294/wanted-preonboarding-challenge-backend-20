package com.exception_study.api.user_account.service;

import com.exception_study.api.user_account.dto.UserAccountDto;
import com.exception_study.api.user_account.dto.request.LoginRequest;
import com.exception_study.api.user_account.repository.UserAccountRepository;
import com.exception_study.global.exception.ErrorCode;
import com.exception_study.global.exception.StudyApplicationException;
import com.exception_study.global.fixture.user.UserInfoFixture;
import com.exception_study.global.fixture.user.UserAccountFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("비즈니스로직 - 회원가입과 로그인")
@SpringBootTest
@ActiveProfiles("test")
public class UserAccountServiceTest {
    @Autowired
    UserAccountService userAccountService;

    @MockBean
    UserAccountRepository userAccountRepository;

    @MockBean
    PasswordEncoder bCryptPasswordEncoder;

    @DisplayName("회원 정보를 입력하면 회원가입에 성공한다.")
    @Test
    public void givenUserInfo_whenSigningUp_thenReturnsSuccess() {
        //given
        UserInfoFixture.TestInfo fixture = UserInfoFixture.get();

        //when
        when(userAccountRepository.existsById(fixture.getUserId())).thenReturn(false);
        when(bCryptPasswordEncoder.encode(fixture.getPassword())).thenReturn("password_encrypt");
        when(userAccountRepository.save(any())).thenReturn(UserAccountFixture.get(fixture.getUserId(), fixture.getPassword(), fixture.getUserName()));

        //then
        assertDoesNotThrow(() -> userAccountService.signUp(UserAccountDto.of(fixture.getUserId(), fixture.getPassword(), fixture.getUserName())));
    }

    @DisplayName("중복된 정보를 입력하면 회원가입에 실패한다.")
    @Test
    public void givenDuplicatedInfo_whenSigningUp_thenReturnsException() {
        //given
        UserInfoFixture.TestInfo fixture = UserInfoFixture.get();

        //when
        when(userAccountRepository.existsById(fixture.getUserId())).thenReturn(true);
        StudyApplicationException exception = Assertions.assertThrows(StudyApplicationException.class,
                () -> userAccountService.signUp(UserAccountDto.of(fixture.getUserId(),fixture.getPassword(),fixture.getUserName()))
        );
        //then
        assertEquals(ErrorCode.USER_ALREADY_EXIST, exception.getErrorCode());
    }

    @DisplayName("회원 정보를 입력하면 로그인에 성공한다.")
    @Test
    void givenUserInfo_whenLoggingIn_thenReturnsSuccess() {
        //given
        UserInfoFixture.TestInfo fixture = UserInfoFixture.get();

        //when
        when(userAccountRepository.findById(fixture.getUserId())).thenReturn(Optional.of(UserAccountFixture.get(fixture.getUserId(), fixture.getPassword(), fixture.getUserName())));
        when(bCryptPasswordEncoder.matches(any(),any())).thenReturn(true);

        //then
        assertDoesNotThrow(() -> userAccountService.login(LoginRequest.of(fixture.getUserId(), fixture.getPassword())));
    }


    @DisplayName("존재하지않는 회원 정보를 입력하면 로그인에 실패한다.")
    @Test
    void givenNotExistsUser_whenLoggingIn_thenReturnsException() {
        //given
        UserInfoFixture.TestInfo fixture = UserInfoFixture.get();

        //when
        when(userAccountRepository.findById(fixture.getUserId())).thenReturn(Optional.empty());
        StudyApplicationException exception = Assertions.assertThrows(StudyApplicationException.class,
                () -> userAccountService.login(LoginRequest.of(fixture.getUserId(), fixture.getPassword())));

        //then
        assertEquals(ErrorCode.USER_NOT_FOUND,exception.getErrorCode());
    }

    @DisplayName("틀린 비밀번호를 입력하면 로그인에 실패한다.")
    @Test
    void givenNotCorrectPassword_whenLoggingIn_thenReturnsException(){
        //given
        UserInfoFixture.TestInfo fixture = UserInfoFixture.get();

        //when
        when(userAccountRepository.findById(fixture.getUserId())).thenReturn(Optional.of(UserAccountFixture.get(fixture.getUserId(), fixture.getPassword(), fixture.getUserName())));
        when(bCryptPasswordEncoder.matches(fixture.getPassword(),"invalid_password")).thenReturn(false);

        StudyApplicationException exception = Assertions.assertThrows(StudyApplicationException.class,
                () -> userAccountService.login(LoginRequest.of(fixture.getUserId(), fixture.getPassword())));

        //then
        assertEquals(ErrorCode.INVALID_PASSWORD,exception.getErrorCode());
    }






}
