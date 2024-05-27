package com.wanted.preonboarding.module.user.service;

import com.wanted.preonboarding.data.auth.AuthModuleHelper;
import com.wanted.preonboarding.document.utils.RedisServiceTest;
import com.wanted.preonboarding.module.common.enums.RedisKey;
import com.wanted.preonboarding.module.user.core.BaseUserInfo;
import com.wanted.preonboarding.module.utils.JsonUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UserRedisFindServiceImplTest extends RedisServiceTest {

    @InjectMocks
    private UserRedisFindServiceImpl userRedisFindService;


    @Test
    @DisplayName("유저 정보 조회 - 존재하는 경우")
    void fetchUserInfo_WhenUserInfoExists() {
        BaseUserInfo baseUserInfo = AuthModuleHelper.toBaseUserInfo();
        String key = RedisKey.USERS.generateKey(baseUserInfo.getEmail());

        String tokenJson = JsonUtils.toJson(baseUserInfo);
        when(valueOperations.get(key)).thenReturn(tokenJson);

        Optional<BaseUserInfo> baseUserInfoOpt = userRedisFindService.fetchUser(baseUserInfo.getEmail());

        assertTrue(baseUserInfoOpt.isPresent());
        verify(valueOperations, times(1)).get(eq(key));
    }

    @Test
    @DisplayName("유저 정보 조회 - 존재하지 않는 경우")
    void fetchUserInfo_WhenUserInfoDoesNotExist() {
        BaseUserInfo baseUserInfo = AuthModuleHelper.toBaseUserInfo();
        String key = RedisKey.USERS.generateKey(baseUserInfo.getEmail());

        when(valueOperations.get(key)).thenReturn(null);

        Optional<BaseUserInfo> baseUserInfoOpt = userRedisFindService.fetchUser(baseUserInfo.getEmail());

        assertTrue(baseUserInfoOpt.isEmpty());
        verify(valueOperations, times(1)).get(eq(key));
    }


}