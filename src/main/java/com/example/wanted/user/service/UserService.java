package com.example.wanted.user.service;

import com.example.wanted.module.exception.LoginCheckFailException;
import com.example.wanted.module.exception.ResourceNotFoundException;
import com.example.wanted.module.jwt.JwtUtil;
import com.example.wanted.user.domain.User;
import com.example.wanted.user.domain.UserCreate;
import com.example.wanted.user.domain.UserLogin;
import com.example.wanted.user.infrastucture.UserEntity;
import com.example.wanted.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public Long create(UserCreate userCreate) {
        User user = userRepository.save(User.from(userCreate));
        return user.getId();
    }

    public String login(UserLogin request) {
        User user = userRepository.findByAccount(request.getAccount()).orElseThrow(() ->
                new ResourceNotFoundException("User", request.getAccount())
        );

        if (!user.getPassword().equals(request.getPassword())) {
            throw new LoginCheckFailException();
        }

        return jwtUtil.createAccessToken(user);
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User", id)
        );
    }
}
