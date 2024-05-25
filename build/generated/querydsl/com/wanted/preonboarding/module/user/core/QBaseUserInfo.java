package com.wanted.preonboarding.module.user.core;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.wanted.preonboarding.module.user.core.QBaseUserInfo is a Querydsl Projection type for BaseUserInfo
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QBaseUserInfo extends ConstructorExpression<BaseUserInfo> {

    private static final long serialVersionUID = -150530730L;

    public QBaseUserInfo(com.querydsl.core.types.Expression<String> phoneNumber, com.querydsl.core.types.Expression<String> email, com.querydsl.core.types.Expression<String> passwordHash, com.querydsl.core.types.Expression<com.wanted.preonboarding.module.user.enums.MemberShip> memberShip) {
        super(BaseUserInfo.class, new Class<?>[]{String.class, String.class, String.class, com.wanted.preonboarding.module.user.enums.MemberShip.class}, phoneNumber, email, passwordHash, memberShip);
    }

}

