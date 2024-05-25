package com.wanted.preonboarding.auth.validator;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordCheckServiceImpl implements PasswordCheckService{

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public boolean checkPassword(String password, String confirmPassword) {
        return !passwordEncoder.matches(password, confirmPassword);
    }
}

