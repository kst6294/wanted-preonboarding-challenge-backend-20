package com.wanted.wantedpreonboardingchallengebackend20.domain.user.repository;

import com.wanted.wantedpreonboardingchallengebackend20.domain.user.entity.User;

public interface UserRepositoryCustom {
    User findByNameAndUserId(String name,String userId);
}
