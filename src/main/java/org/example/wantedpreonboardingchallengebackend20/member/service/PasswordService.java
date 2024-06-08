package org.example.wantedpreonboardingchallengebackend20.member.service;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    public String encryptPassword(String password) {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password);
    }

    public boolean checkPassword(String password, String encryptedPassword) {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder().matches(password, encryptedPassword);
    }
}
