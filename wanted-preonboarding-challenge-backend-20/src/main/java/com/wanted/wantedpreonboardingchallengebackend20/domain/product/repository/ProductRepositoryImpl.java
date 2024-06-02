package com.wanted.wantedpreonboardingchallengebackend20.domain.product.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.wantedpreonboardingchallengebackend20.domain.product.entity.ProductState;
import com.wanted.wantedpreonboardingchallengebackend20.domain.product.entity.QProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    private QProduct product=new QProduct("product");
    @Override
    public void modifyProductState(Long productId){
        queryFactory.update(product)
                .set(product.state, ProductState.PROCESSING)
                .where(product.id.eq(productId))
                .execute();
    }
    @Override
    public void sellProduct(Long productId){
        queryFactory.update(product)
                .set(product.state,ProductState.SOLD)
                .where(product.id.eq(productId))
                .execute();
    }
}
