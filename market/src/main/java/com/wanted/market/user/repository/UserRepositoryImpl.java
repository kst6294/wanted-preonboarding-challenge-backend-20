package com.wanted.market.user.repository;
import com.wanted.market.user.domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository{
    private long autoIncrementId = 1L;
    private final Map<Long, User> store = new HashMap<>();

    @Override
    public User save(User user) {
        store.put(autoIncrementId++, user);
        return user;
    }

    @Override
    public Optional<User> findByMemberId(Long userId) {
        return Optional.ofNullable(store.get(userId));
    }
}
