package com.example.wantedmarketapi.infrastructure.user;

import com.example.wantedmarketapi.domain.user.User;
import com.example.wantedmarketapi.domain.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserStoreImpl implements UserStore {

    private final UserRepository userRepository;
    @Override
    public User store(User user) {
        return userRepository.save(user);
    }
}
