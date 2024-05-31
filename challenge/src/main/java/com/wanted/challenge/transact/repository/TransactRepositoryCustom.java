package com.wanted.challenge.transact.repository;

import com.wanted.challenge.product.entity.Product;
import com.wanted.challenge.transact.model.TransactState;
import java.util.List;
import java.util.Set;

public interface TransactRepositoryCustom {

    List<TransactState> retrieveAllTransactState(Long transactId);

    List<TransactState> retrieveAllTransactState(Long buyerId, Long productId);

    TransactState retrieveLastTransactState(Long buyerId, Long productId);

    Set<TransactState> retrieveDistinctProductTransactStates(Product product);
}
