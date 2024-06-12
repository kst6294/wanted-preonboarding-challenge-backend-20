package com.example.wantedmarketapi.application.user;

import com.example.wantedmarketapi.domain.user.UserCommand;
import com.example.wantedmarketapi.domain.user.UserInfo;
import com.example.wantedmarketapi.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    public UserInfo registerUser(UserCommand command) {
        UserInfo userInfo = userService.registerUser(command);
        return userInfo;
    }

}
