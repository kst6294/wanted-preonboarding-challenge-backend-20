package com.wanted.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.dto.QSellDto;
import com.wanted.dto.SellDto;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.wanted.entity.QMember.member;
import static com.wanted.entity.QProduct.product;

public class ProductRepositoryImpl implements ProductRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ProductRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);

    }

    @Override
    public List<SellDto> findAllSellDtos() {
        return queryFactory
                .select(new QSellDto(
                        product.id.as("product_id"),
                        member.email,
                        product.name,
                        product.price,
                        product.stockQuantity.as("stock_quantity"),
                        product.status.stringValue().as("status")))
                .from(product)
                .join(product.member, member)
                .fetch();
    }


}
