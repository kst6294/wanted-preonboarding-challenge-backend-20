package com.exception_study.global.fixture.user;

import com.exception_study.api.user_account.entity.UserAccount;

public class UserAccountFixture {

    public static UserAccount get(String userId, String password, String userName){
        UserAccount entity = new UserAccount();
        entity.setUserId(userId);
        entity.setPassword(password);
        entity.setUserName(userName);
        return entity;
    }
}
