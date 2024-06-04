package com.wanted.market.member.infra;

import com.wanted.market.member.domain.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class NoOpPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return String.valueOf(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return Objects.equals(rawPassword, encodedPassword);
    }
}
