package com.want.BEProject.orders.repository;

import com.want.BEProject.orders.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long> {


    List<Orders> findAllByBuyerId(long id);
}
