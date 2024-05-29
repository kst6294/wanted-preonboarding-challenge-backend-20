package com.example.wanted_market.service;

import com.example.wanted_market.domain.User;
import com.example.wanted_market.dto.request.AuthLoginRequestDto;
import com.example.wanted_market.dto.request.AuthRegisterRequestDto;
import com.example.wanted_market.repository.UserRepository;
import com.example.wanted_market.type.ERole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public void register(AuthRegisterRequestDto authRegisterRequestDto) {
        // 이메일 중복 확인
        if(userRepository.existsByEmail(authRegisterRequestDto.email()))
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");

        // User 빌드 & 저장
        userRepository.save(User.builder()
                .email(authRegisterRequestDto.email())
                .password(passwordEncoder.encode(authRegisterRequestDto.password()))
                .nickname(authRegisterRequestDto.nickname())
                .role(ERole.USER).build());
    }

    // 로그인
    @Transactional(readOnly = true)
    public Long login(AuthLoginRequestDto authLoginRequestDto) {
        // 이메일 확인
        User user = userRepository.findByEmail(authLoginRequestDto.email()).orElseThrow(
                () -> { throw new IllegalArgumentException("존재하지 않는 이메일입니다."); }
        );

        // 비밀번호 확인
        if(!passwordEncoder.matches(authLoginRequestDto.password(), user.getPassword()))
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");

        return user.getId();
    }
}
