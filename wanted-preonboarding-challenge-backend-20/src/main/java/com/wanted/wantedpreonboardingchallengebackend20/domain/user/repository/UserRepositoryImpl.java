package com.wanted.wantedpreonboardingchallengebackend20.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.wantedpreonboardingchallengebackend20.domain.user.entity.QUser;
import com.wanted.wantedpreonboardingchallengebackend20.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    private QUser user=new QUser("user");
    @Override
    public User findByNameAndUserId(String name,String userId){
        return queryFactory.selectFrom(user)
                .where(user.name.eq(name).and(user.userId.eq(userId)))
                .fetchOne();
    }
}
