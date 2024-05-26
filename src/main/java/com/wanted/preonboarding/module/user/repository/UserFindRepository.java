package com.wanted.preonboarding.module.user.repository;

import com.wanted.preonboarding.module.user.core.BaseUserInfo;
import com.wanted.preonboarding.module.user.entity.Users;

import java.util.Optional;

public interface UserFindRepository {

    Optional<BaseUserInfo> fetchUserInfo(String email);

    Optional<Users> fetchUserEntity(String email);
}
