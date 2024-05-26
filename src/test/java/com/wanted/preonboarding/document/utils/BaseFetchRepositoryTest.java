package com.wanted.preonboarding.document.utils;

import com.wanted.preonboarding.infra.config.querydsl.TestQueryDslConfig;
import com.wanted.preonboarding.module.user.repository.UserFindRepositoryImpl;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({TestQueryDslConfig.class,
        UserFindRepositoryImpl.class
})
public abstract class BaseFetchRepositoryTest {

    @Autowired
    private EntityManager em;

    protected EntityManager getEntityManager(){
        return em;
    }


    protected void flushAndClear() {
        em.flush();
        em.clear();
    }

}
