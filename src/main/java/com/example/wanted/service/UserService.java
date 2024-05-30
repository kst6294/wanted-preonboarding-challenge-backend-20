package com.example.wanted.service;

import com.example.wanted.model.User;
import com.example.wanted.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public void register(User user) {
        String rawPassword = user.getPassword();//진짜 password
        String encPassword = encoder.encode(rawPassword);
        user.setPassword(encPassword);//암호화된 password
        user.setRole("ROLE_USER");
        userRepository.save(user);
    }
}
