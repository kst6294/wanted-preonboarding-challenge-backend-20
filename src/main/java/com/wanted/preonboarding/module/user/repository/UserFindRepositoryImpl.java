package com.wanted.preonboarding.module.user.repository;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.preonboarding.module.product.entity.QProduct;
import com.wanted.preonboarding.module.product.enums.ProductStatus;
import com.wanted.preonboarding.module.user.core.BaseUserInfo;
import com.wanted.preonboarding.module.user.core.QBaseUserInfo;
import com.wanted.preonboarding.module.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.wanted.preonboarding.module.product.entity.QProduct.product;
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

    @Override
    public Optional<Users> fetchUserEntity(String email) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(users)
                        .where(emailEq(email))
                        .fetchOne()
        );
    }


    public List<Users> fetchAllUsersWithProduct() {
        return queryFactory
                .selectFrom(users)
                .innerJoin(users.products, product).fetchJoin()
                .where(onStock())
                .fetch();
    }

    private BooleanExpression emailEq(String email){
        return users.email.eq(email);
    }

    private BooleanExpression onStock(){
        return product.quantity.gt(0)
                .and(product.productStatus.ne(ProductStatus.OUT_OF_STOCK));
    }

}
