package com.wanted.preonboarding.module.user.service;

import com.wanted.preonboarding.module.user.core.UserInfo;

public interface UserRedisQueryService {
    void saveInCache(UserInfo userInfo);
}
