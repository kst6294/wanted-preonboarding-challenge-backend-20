package com.wanted.preonboarding.module.user.service;


import com.wanted.preonboarding.module.user.core.BaseUserInfo;

public interface UserFindService {

    BaseUserInfo fetchUserInfo(String email);

}
