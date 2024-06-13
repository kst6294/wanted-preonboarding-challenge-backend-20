package org.example.wantedmarket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.wantedmarket.dto.user.JoinDto;
import org.example.wantedmarket.exception.CustomException;
import org.example.wantedmarket.exception.ErrorCode;
import org.example.wantedmarket.model.User;
import org.example.wantedmarket.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;


    @Transactional
    public void joinUser(JoinDto joinDto) {
        String username = joinDto.getUsername();
        String password = joinDto.getPassword();

        validateUsername(username);

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(bCryptPasswordEncoder.encode(password));
        newUser.setRole("ROLE_ADMIN");

        userRepository.save(newUser);
    }

    private void validateUsername(String username) {
        User findUser = userRepository.findByUsername(username);
        if (findUser != null) {
            throw new CustomException(ErrorCode.USERNAME_ALREADY_IN_USE);
        }
    }

}
