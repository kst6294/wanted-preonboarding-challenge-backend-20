package org.example.preonboarding.order.repository;

import org.example.preonboarding.member.model.entity.Member;
import org.example.preonboarding.order.model.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByBuyer(Member buyer);
}
