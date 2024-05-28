package com.wanted.market.product.repository;

import com.wanted.market.product.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(Long productId);
    List<Product> findAll();
    List<Product> findPurchasedProductsByBuyerId(Long memberId);
    List<Product> findReservedProductsByMemberId(Long memberId);
    List<Product> findProductsBySellerIdAndBuyerId(Long sellerId, Long buyerId);
    void deleteById(Long ProductId);
}
