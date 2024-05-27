package com.wanted.preonboarding.auth.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PasswordCheckerImplTest {

    @InjectMocks
    private PasswordCheckerImpl passwordCheckService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    @DisplayName("비밀번호가 유효한 경우")
    void validPassword() {
        String rawPassword = "password";
        String encodedPassword = "encodedPassword";

        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        boolean result = passwordCheckService.checkPassword(rawPassword, encodedPassword);

        assertFalse(result, "비밀번호가 유효할 경우 false를 반환해야 합니다.");
    }

    @Test
    @DisplayName("비밀번호가 유효하지 않은 경우")
    void invalidPassword() {
        String rawPassword = "password";
        String encodedPassword = "encodedPassword";

        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        boolean result = passwordCheckService.checkPassword(rawPassword, encodedPassword);

        assertTrue(result, "비밀번호가 유효하지 않을 경우 true를 반환해야 합니다.");
    }
}