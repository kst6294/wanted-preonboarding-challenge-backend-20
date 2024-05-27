package com.example.wantedmarketapi.domain.member;

import java.util.regex.Pattern;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.wantedmarketapi.exception.GlobalErrorCode;
import com.example.wantedmarketapi.exception.custom.MemberException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class Password {
    private static final String PASSWORD_REGEX =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+-=\\[\\]{}|;':\",./<>?~`\\\\])[A-Za-z\\d!@#$%^&*()_+\\-=\\[\\]{}|;':\",./<>?~`\\\\]{9,16}";

    @Column(name = "password", nullable = false, columnDefinition = "TEXT")
    private String encryptedPassword;

    public static Password encrypt(String plainPassword, BCryptPasswordEncoder encoder) {
        if (!isPasswordValid(plainPassword)) {
            throw new MemberException(GlobalErrorCode.NOT_VALID_PASSWORD);
        }
        return new Password(encoder.encode(plainPassword));
    }

    public static Boolean isPasswordValid(String plainPassword) {
        return Pattern.matches(PASSWORD_REGEX, plainPassword);
    }

    public Boolean isSamePassword(String plainPassword, BCryptPasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(plainPassword, encryptedPassword);
    }
}