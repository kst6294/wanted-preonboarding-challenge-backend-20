package com.wanted.preonboarding.module.user.service;

import com.wanted.preonboarding.data.auth.AuthModuleHelper;
import com.wanted.preonboarding.document.utils.RedisServiceTest;
import com.wanted.preonboarding.module.common.enums.RedisKey;
import com.wanted.preonboarding.module.user.core.BaseUserInfo;
import com.wanted.preonboarding.module.utils.JsonUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class UserRedisQueryServiceImplTest extends RedisServiceTest {

    @InjectMocks
    private UserRedisQueryServiceImpl userRedisQueryService;

    @Test
    @DisplayName("UserInfo 저장 테스트")
    void saveUserInfo() {
        BaseUserInfo baseUserInfo = AuthModuleHelper.toBaseUserInfo();
        String tokenJson = JsonUtils.toJson(baseUserInfo);
        String key = userRedisQueryService.generateKey(RedisKey.USERS, baseUserInfo.getEmail());
        userRedisQueryService.saveInCache(baseUserInfo);
        verify(valueOperations, times(1)).set(eq(key), eq(tokenJson), eq(RedisKey.USERS.getSecondDuration()));
    }

}