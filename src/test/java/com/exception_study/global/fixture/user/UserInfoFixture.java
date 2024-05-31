package com.exception_study.global.fixture.user;

import lombok.Data;

public class UserInfoFixture {

    public static TestInfo get(){
        TestInfo info = new TestInfo();
        info.setUserId("test");
        info.setPassword("1234");
        info.setUserName("테스터");
        return info;
    }

    @Data
    public static class TestInfo{
        private String userId;
        private String password;
        private String userName;
    }

}
