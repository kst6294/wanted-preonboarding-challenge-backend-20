package com.market.wanted;

import com.market.wanted.member.entity.Member;
import com.market.wanted.product.entity.Product;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

//@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;
    @PostConstruct
    public void init() {
        initService.dbInit1();
    }

//    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;
        public void dbInit1() {
            Member seller = new Member("1234", "seller", "seller@test.com");
            Member buyer = new Member("1234", "buyer", "buyer@test.com");
            em.persist(seller);
            em.persist(buyer);
            Product product = new Product("itemA", 1000, buyer);
            em.persist(product);
        }
    }

}
