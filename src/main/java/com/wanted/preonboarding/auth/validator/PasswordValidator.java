package com.wanted.preonboarding.auth.validator;

import com.wanted.preonboarding.auth.dto.CreateAuthToken;
import com.wanted.preonboarding.module.exception.user.NotFoundUserException;
import com.wanted.preonboarding.module.user.core.BaseUserInfo;
import com.wanted.preonboarding.module.user.service.UserFindService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PasswordValidator implements ConstraintValidator<ValidPassword, CreateAuthToken> {

    protected static final String INCORRECT_PASSWORD_MESSAGE = "유효하지 않은 비밀번호 입니다.";
    private final PasswordChecker passwordChecker;
    private final UserFindService userFindService;

    @Override
    public boolean isValid(CreateAuthToken createAuthToken, ConstraintValidatorContext context) {
        if (createAuthToken == null) {
            return false;
        }

        try {
            BaseUserInfo baseUserInfo = userFindService.fetchUserInfo(createAuthToken.getEmail());
            String passwordHash = baseUserInfo.getPasswordHash();
            if (passwordChecker.checkPassword(createAuthToken.getPassword(), passwordHash)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(INCORRECT_PASSWORD_MESSAGE)
                        .addPropertyNode("password")
                        .addConstraintViolation();
                return false;
            }
            return true;
        } catch (NotFoundUserException e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode("email")
                    .addConstraintViolation();
            return false;
        }
    }
}
