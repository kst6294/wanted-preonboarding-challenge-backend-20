package com.challenge.market.product.service;

import com.challenge.market.product.domain.Product;
import com.challenge.market.product.dto.ProductResponse;
import com.challenge.market.product.exception.ProductRegistrationFailedException;
import com.challenge.market.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void add(Product product){

        try{
            productRepository.save(product);

        } catch (DataIntegrityViolationException e){
            throw new ProductRegistrationFailedException("DataIntegrityViolationException 으로인한 제품 등록 실패",e);
        }

    }

    public Product get(long id) {
        return productRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(Product::of)
                .collect(Collectors.toList());

    }

}
