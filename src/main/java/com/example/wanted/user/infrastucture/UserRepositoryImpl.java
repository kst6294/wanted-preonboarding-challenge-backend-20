package com.example.wanted.user.infrastucture;

import com.example.wanted.user.domain.User;
import com.example.wanted.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public User findByAccount(String account) {
        return null;
    }
}
