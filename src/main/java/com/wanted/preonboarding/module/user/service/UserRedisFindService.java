package com.wanted.preonboarding.module.user.service;

import com.wanted.preonboarding.module.user.core.BaseUserInfo;
import com.wanted.preonboarding.module.user.entity.Users;

import java.util.Optional;

public interface UserRedisFindService {
    Optional<BaseUserInfo> fetchUser(String email);
    Optional<Users> fetchUserEntity(String email);
}
