package com.wanted.preonboarding.module.user.service;


import com.wanted.preonboarding.module.exception.user.NotFoundUserException;
import com.wanted.preonboarding.module.user.core.BaseUserInfo;
import com.wanted.preonboarding.module.user.entity.Users;
import com.wanted.preonboarding.module.user.repository.UserFindRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserFindServiceImpl implements UserFindService{

    private final UserRedisQueryService userRedisQueryService;
    private final UserRedisFindService userRedisFindService;
    private final UserFindRepository userFindRepository;

    @Override
    public BaseUserInfo fetchUserInfo(String email) {
        Optional<BaseUserInfo> baseUserInfo = userRedisFindService.fetchUser(email);
        return baseUserInfo.orElseGet(() -> fetchUserInfoInDb(email));
    }

    @Override
    public Users fetchUserEntity(String email) {
        Optional<Users> users = userRedisFindService.fetchUserEntity(email);
        return users.orElseGet(()-> fetchUserEntityInDb(email));
    }

    public Users fetchUserEntityInDb(String email) {
        Users users = userFindRepository.fetchUserEntity(email).orElseThrow(() -> new NotFoundUserException(email));
        userRedisQueryService.saveInCache(users);
        return users;
    }


    private BaseUserInfo fetchUserInfoInDb(String email) {
        BaseUserInfo baseUserInfo = userFindRepository.fetchUserInfo(email).orElseThrow(() -> new NotFoundUserException(email));
        userRedisQueryService.saveInCache(baseUserInfo);
        return baseUserInfo;
    }

}
