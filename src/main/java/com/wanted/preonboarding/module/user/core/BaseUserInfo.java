package com.wanted.preonboarding.module.user.core;

import com.querydsl.core.annotations.QueryProjection;
import com.wanted.preonboarding.module.user.enums.MemberShip;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseUserInfo implements UserInfo {

    private String phoneNumber;
    private String email;
    private String passwordHash;
    private MemberShip memberShip;

    @QueryProjection
    public BaseUserInfo(String phoneNumber, String email, String passwordHash, MemberShip memberShip) {
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.passwordHash = passwordHash;
        this.memberShip = memberShip;
    }


}
