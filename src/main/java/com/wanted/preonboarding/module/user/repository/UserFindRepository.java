package com.wanted.preonboarding.module.user.repository;

import com.wanted.preonboarding.module.user.core.BaseUserInfo;

import java.util.Optional;

public interface UserFindRepository {

    Optional<BaseUserInfo> fetchUserInfo(String email);
}
