package com.wanted.preonboarding.auth.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.wanted.preonboarding.auth.validator.PasswordValidator.INCORRECT_PASSWORD_MESSAGE;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface ValidPassword {
    String message() default INCORRECT_PASSWORD_MESSAGE;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}