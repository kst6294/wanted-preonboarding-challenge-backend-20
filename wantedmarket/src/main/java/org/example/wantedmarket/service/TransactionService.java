package org.example.wantedmarket.service;

import lombok.RequiredArgsConstructor;
import org.example.wantedmarket.repository.jpa.OrderJpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final OrderJpaRepository orderJpaRepository;
}
