package com.wanted.challenge.transact.repository;

import com.wanted.challenge.product.entity.Product;
import com.wanted.challenge.transact.model.TransactDetail;
import java.util.Set;

public interface TransactRepositoryCustom {

    TransactDetail retrieveLastTransactDetail(Long buyerId, Long productId);

    boolean isPurchaseAlready(Long buyerId, Long productId);

    Set<TransactDetail> retrieveProductTransactDetails(Product product);
}
