package com.example.wanted.service;

import com.example.wanted.model.Transaction;
import com.example.wanted.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public void insert(Transaction transaction) {
        orderRepository.save(transaction);
    }

//    public List<Transaction> list(Long id) {
//        return orderRepository.findByProductId(id);
//    }

    public Long findByBuyerId(Long id) {
        return orderRepository.findByBuyerId(id);
    }

    public List<Transaction> purchaseList(Long id) {
        return orderRepository.findByBuyId(id);
    }

    public List<Transaction> sellList(Long id) {
        return orderRepository.findBySellId(id);
    }

//    public List<Transaction> allTransactions(Long id) {
//        return orderRepository.findAllByUserId(id);
//    }
}
