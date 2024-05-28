package com.example.wanted.service.user;

import com.example.wanted.common.exception.LoginCheckFailException;
import com.example.wanted.common.exception.ResourceNotFoundException;
import com.example.wanted.common.jwt.JwtUtil;
import com.example.wanted.controller.user.request.UserCreateRequest;
import com.example.wanted.controller.user.request.UserLoginRequest;
import com.example.wanted.domain.user.UserEntity;
import com.example.wanted.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public Long create(UserCreateRequest request) {
        UserEntity userEntity = userRepository.save(request.toEntity());
        return userEntity.getId();
    }

    public String login(UserLoginRequest request) {
        UserEntity userEntity = userRepository.findByAccount(request.getAccount()).orElseThrow(() ->
                new ResourceNotFoundException("User", request.getAccount())
        );

        if (!userEntity.getPassword().equals(request.getPassword())) {
            throw new LoginCheckFailException();
        }

        return jwtUtil.createAccessToken(userEntity);
    }

    public UserEntity getById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User", id)
        );
    }
}
