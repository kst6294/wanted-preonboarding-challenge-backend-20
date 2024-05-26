package com.wanted.preonboarding.auth.validator;

public interface PasswordCheckService {

    boolean checkPassword(String password, String confirmPassword);
}
