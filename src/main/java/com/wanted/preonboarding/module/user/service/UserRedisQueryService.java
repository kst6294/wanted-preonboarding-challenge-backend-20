package com.wanted.preonboarding.module.user.service;

import com.wanted.preonboarding.module.user.core.UserInfo;
import com.wanted.preonboarding.module.user.entity.Users;

public interface UserRedisQueryService {
    void saveInCache(UserInfo userInfo);
    void saveInCache(Users users);
}
