package com.example.wanted.user.service;

import com.example.wanted.module.exception.InvalidCredentialsException;
import com.example.wanted.module.exception.ResourceAlreadyException;
import com.example.wanted.module.exception.ResourceNotFoundException;
import com.example.wanted.user.domain.User;
import com.example.wanted.user.domain.UserCreate;
import com.example.wanted.user.domain.UserLogin;
import com.example.wanted.user.service.port.JwtUtil;
import com.example.wanted.user.service.port.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Builder
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public User create(UserCreate userCreate) {
        Optional<User> findUser = userRepository.findByAccount(userCreate.getAccount());
        if(findUser.isPresent()) {
            //todo: 예외 처리 수정
            throw new ResourceAlreadyException("User", "account" ,userCreate.getAccount());
        }

        User user = userRepository.save(User.from(userCreate));
        return user;
    }


    public String login(UserLogin request) {
        User user = userRepository.findByAccount(request.getAccount()).orElseThrow(() ->
            new InvalidCredentialsException("계정혹은 패스워드가 잘못되었습니다.")
        );

        if (!user.getPassword().equals(request.getPassword())) {
            throw new InvalidCredentialsException("계정혹은 패스워드가 잘못되었습니다.");
        }

        return jwtUtil.createAccessToken(user);
    }

    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User", id)
        );
    }
}
