package com.wanted.market.user.repository;

import com.wanted.market.user.domain.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findByMemberId(Long memberId);

}
