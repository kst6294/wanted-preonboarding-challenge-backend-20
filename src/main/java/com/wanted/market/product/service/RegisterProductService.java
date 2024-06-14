package com.wanted.market.product.service;

import com.wanted.market.common.exception.InvalidRequestException;
import com.wanted.market.product.domain.Product;
import com.wanted.market.product.domain.ProductRepository;
import com.wanted.market.product.domain.StockRegister;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegisterProductService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ProductRepository productRepository;
    private final StockRegister stockRegister;

    public RegisterProductService(ProductRepository productRepository, StockRegister stockRegister) {
        this.productRepository = productRepository;
        this.stockRegister = stockRegister;
    }

    @Timed("RegisterProductService.register")
    public Long register(Command cmd) throws InvalidRequestException {
        logger.info("start register new product: {}", cmd);
        Product newProduct = new Product(cmd.sellerId(), cmd.name(), cmd.price(), cmd.quantity());
        productRepository.saveAndFlush(newProduct);
        logger.info("product registered: {}", newProduct.getId());
        newProduct.open(stockRegister);
        logger.info("stock registered");
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
