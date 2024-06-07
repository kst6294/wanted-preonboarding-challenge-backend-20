package com.wanted.market.product.service;

import com.wanted.market.common.exception.InvalidRequestException;
import com.wanted.market.product.domain.Product;
import com.wanted.market.product.domain.ProductRepository;
import com.wanted.market.product.domain.StockRegister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegisterProductService {
    private final ProductRepository productRepository;
    private final StockRegister stockRegister;

    public RegisterProductService(ProductRepository productRepository, StockRegister stockRegister) {
        this.productRepository = productRepository;
        this.stockRegister = stockRegister;
    }

    public Long register(Command cmd) throws InvalidRequestException {
        Product newProduct = new Product(cmd.sellerId(), cmd.name(), cmd.price(), cmd.quantity());
        productRepository.saveAndFlush(newProduct);
        newProduct.open(stockRegister);
        return newProduct.getId();
    }

    public record Command(
            Long sellerId,
            String name,
            Integer price,
            Integer quantity
    ) {
    }
}
