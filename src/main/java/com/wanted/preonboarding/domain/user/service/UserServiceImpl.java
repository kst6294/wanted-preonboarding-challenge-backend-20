package com.wanted.preonboarding.domain.user.service;

import com.wanted.preonboarding.domain.user.dto.request.AddUserRequest;
import com.wanted.preonboarding.domain.user.repository.UserRepository;
import com.wanted.preonboarding.global.exception.entity.RestApiException;
import com.wanted.preonboarding.global.exception.errorCode.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public void join(AddUserRequest request) {
        // 1. 유저 확인
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RestApiException(UserErrorCode.EMAIL_ALREADY_EXISTS, "이메일이 ["+request.getEmail()+"] 인 유저가 이미 존재합니다.");
        }

        // 2. 비밀번호 암호화
        String encryptedPassword = bCryptPasswordEncoder.encode(request.getPassword());

        // 3. 저장
        userRepository.save(request.toEntity(encryptedPassword));
    }
}
