package com.wanted.challenge.product.service;

import com.wanted.challenge.account.entity.Account;
import com.wanted.challenge.account.model.AccountDetail;
import com.wanted.challenge.account.repository.AccountRepository;
import com.wanted.challenge.product.entity.Product;
import com.wanted.challenge.product.model.Price;
import com.wanted.challenge.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;

    public Long register(String name, Price price, AccountDetail accountDetail) {
        Long sellerId = accountDetail.getAccountId();
        Account seller = accountRepository.getReferenceById(sellerId);

        Product product = new Product(seller, name, price);
        productRepository.save(product);

        return product.getId();
    }
}
