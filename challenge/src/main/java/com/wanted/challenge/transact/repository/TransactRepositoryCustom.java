package com.wanted.challenge.transact.repository;

import com.wanted.challenge.product.entity.Product;
import com.wanted.challenge.transact.model.TransactState;
import java.util.Set;

public interface TransactRepositoryCustom {

    TransactState retrieveLastTransactDetail(Long buyerId, Long productId);

    boolean isPurchaseAlready(Long buyerId, Long productId);

    Set<TransactState> retrieveProductTransactDetails(Product product);
}
