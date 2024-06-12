package com.example.wantedmarketapi.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserStore userStore;
    private final UserReader userReader;
    @Override
    public UserInfo registerUser(UserCommand command) {
        User initUser = command.toEntity();
        User user = userStore.store(initUser);
        return new UserInfo(user);
    }
}
