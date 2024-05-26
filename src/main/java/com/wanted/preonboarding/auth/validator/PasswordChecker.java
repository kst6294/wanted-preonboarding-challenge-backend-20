package com.wanted.preonboarding.auth.validator;

public interface PasswordChecker {

    boolean checkPassword(String password, String confirmPassword);
}
