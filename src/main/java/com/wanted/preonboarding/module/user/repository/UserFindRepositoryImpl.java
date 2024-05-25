package com.wanted.preonboarding.module.user.repository;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.preonboarding.module.user.core.BaseUserInfo;
import com.wanted.preonboarding.module.user.core.QBaseUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.wanted.preonboarding.module.user.entity.QUsers.users;

@Repository
@RequiredArgsConstructor
public class UserFindRepositoryImpl implements UserFindRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<BaseUserInfo> fetchUserInfo(String email) {
        return Optional.ofNullable(
                queryFactory.select(
                            new QBaseUserInfo(
                                    users.phoneNumber,
                                    users.email,
                                    users.passwordHash,
                                    users.memberShip
                            )
                        )
                        .from(users)
                        .where(emailEq(email))
                        .fetchOne()
        );
    }


    private BooleanExpression emailEq(String email){
        return users.email.eq(email);
    }


}
