package com.api.jellomarket.config

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QuerydslConfiguration(
    private val entityManager: EntityManager
) {
    @Bean
    fun querydsl() = JPAQueryFactory(entityManager)
}