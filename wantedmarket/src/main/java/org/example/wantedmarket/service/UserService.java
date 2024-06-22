package org.example.wantedmarket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.wantedmarket.dto.user.UserJoinRequest;
import org.example.wantedmarket.dto.user.UserResponse;
import org.example.wantedmarket.exception.CustomException;
import org.example.wantedmarket.exception.ErrorCode;
import org.example.wantedmarket.domain.User;
import org.example.wantedmarket.repository.jpa.UserJpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserJpaRepository userRepository;


    @Transactional
    public UserResponse joinUser(UserJoinRequest joinDto) {
        String username = joinDto.getUsername();
        String password = joinDto.getPassword();

        validateUsername(username);

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(bCryptPasswordEncoder.encode(password));
        newUser.setRole("ROLE_USER");

        userRepository.save(newUser);

        return UserResponse.from(newUser);
    }

    private void validateUsername(String username) {
        User findUser = userRepository.findByUsername(username);
        if (findUser != null) {
            throw new CustomException(ErrorCode.USERNAME_ALREADY_IN_USE);
        }
    }

}
