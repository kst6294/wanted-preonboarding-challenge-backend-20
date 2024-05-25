package com.wanted.preonboarding.module.user.core;

import com.wanted.preonboarding.module.user.enums.MemberShip;

public interface UserInfo {

    String getPhoneNumber();
    String getEmail();
    String getPasswordHash();
    MemberShip getMemberShip();
}
