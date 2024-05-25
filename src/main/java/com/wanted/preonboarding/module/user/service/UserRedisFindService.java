package com.wanted.preonboarding.module.user.service;

import com.wanted.preonboarding.module.user.core.BaseUserInfo;

import java.util.Optional;

public interface UserRedisFindService {
    Optional<BaseUserInfo> fetchUser(String email);
}
