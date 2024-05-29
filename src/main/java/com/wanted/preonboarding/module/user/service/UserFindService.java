package com.wanted.preonboarding.module.user.service;


import com.wanted.preonboarding.module.user.core.BaseUserInfo;
import com.wanted.preonboarding.module.user.entity.Users;

public interface UserFindService {

    BaseUserInfo fetchUserInfo(String email);

    Users fetchUserEntity(String email);
}
