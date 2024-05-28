package com.wanted.market.product.repository;

import com.wanted.market.product.domain.Product;
import com.wanted.market.product.domain.ProductStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {
    private long autoIncrementId = 1L;
    private final Map<Long, Product> store = new HashMap<>();

    @Override
    public Product save(Product product) {
        store.put(autoIncrementId++, product);
        return product;
    }

    @Override
    public Optional<Product> findById(Long productId) {
        return Optional.ofNullable(store.get(productId));
    }

    @Override
    public List<Product> findAll() {
        return store.entrySet().stream().map(entry -> store.get(entry.getKey())).toList();
    }

    @Override
    public List<Product> findPurchasedProductsByBuyerId(Long memberId) {
        return store.entrySet().stream().map(entry -> store.get(entry.getKey()))
                .filter(product -> product.getBuyerId().equals(memberId))
                .filter(product -> product.getStatus().equals(ProductStatus.SOLD))
                .toList();
    }

    @Override
    public List<Product> findReservedProductsByMemberId(Long memberId) {
        return store.entrySet().stream().map(entry -> store.get(entry.getKey()))
                .filter(product -> product.getSellerId().equals(memberId) || product.getBuyerId().equals(memberId))
                .filter(product -> product.getStatus().equals(ProductStatus.RESERVED))
                .toList();
    }

    @Override
    public List<Product> findProductsBySellerIdAndBuyerId(Long sellerId, Long buyerId) {
        return store.entrySet().stream().map(entry -> store.get(entry.getKey()))
                .filter(product -> product.getSellerId().equals(sellerId))
                .filter(product -> product.getBuyerId().equals(buyerId))
                .toList();
    }

    @Override
    public void deleteById(Long productId) {
        store.remove(productId);
    }
}