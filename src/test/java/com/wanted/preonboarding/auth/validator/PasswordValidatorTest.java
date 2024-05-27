package com.wanted.preonboarding.auth.validator;

import com.wanted.preonboarding.auth.dto.CreateAuthToken;
import com.wanted.preonboarding.data.auth.AuthModuleHelper;
import com.wanted.preonboarding.module.exception.user.NotFoundUserException;
import com.wanted.preonboarding.module.user.core.BaseUserInfo;
import com.wanted.preonboarding.module.user.service.UserFindService;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PasswordValidatorTest {

    @InjectMocks
    private PasswordValidator passwordValidator;

    @Mock
    private PasswordChecker passwordChecker;

    @Mock
    private UserFindService userFindService;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nodeBuilderCustomizableContext;


    @Test
    @DisplayName("비밀번호가 유효한 경우")
    void validPassword() {
        CreateAuthToken createAuthToken = AuthModuleHelper.toCreateAuthToken();
        BaseUserInfo baseUserInfo = AuthModuleHelper.toBaseUserInfo();

        when(userFindService.fetchUserInfo(createAuthToken.getEmail())).thenReturn(baseUserInfo);
        when(passwordChecker.checkPassword(anyString(), anyString())).thenReturn(false);

        assertTrue(passwordValidator.isValid(createAuthToken, constraintValidatorContext));
    }

    @Test
    @DisplayName("비밀번호가 유효하지 않은 경우")
    void invalidPassword() {
        CreateAuthToken createAuthToken = AuthModuleHelper.toCreateAuthToken();
        BaseUserInfo baseUserInfo = AuthModuleHelper.toBaseUserInfo();

        when(userFindService.fetchUserInfo(createAuthToken.getEmail())).thenReturn(baseUserInfo);
        when(passwordChecker.checkPassword(anyString(), anyString())).thenReturn(true);

        when(constraintValidatorContext.buildConstraintViolationWithTemplate(anyString()))
                .thenReturn(constraintViolationBuilder);
        when(constraintViolationBuilder.addPropertyNode(anyString()))
                .thenReturn(nodeBuilderCustomizableContext);

        assertFalse(passwordValidator.isValid(createAuthToken, constraintValidatorContext));
    }

    @Test
    @DisplayName("회원 찾을 수 없는 경우")
    void userNotFound() {
        CreateAuthToken createAuthToken = AuthModuleHelper.toCreateAuthToken();
        when(userFindService.fetchUserInfo(createAuthToken.getEmail())).thenThrow(new NotFoundUserException("notfound@example.com"));

        when(constraintValidatorContext.buildConstraintViolationWithTemplate(anyString()))
                .thenReturn(constraintViolationBuilder);
        when(constraintViolationBuilder.addPropertyNode(anyString()))
                .thenReturn(nodeBuilderCustomizableContext);

        assertFalse(passwordValidator.isValid(createAuthToken, constraintValidatorContext));
    }

}