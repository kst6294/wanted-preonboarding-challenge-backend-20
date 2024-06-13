package com.challenge.market.domain.product.service;

import com.challenge.market.domain.product.entity.Product;
import com.challenge.market.web.product.dto.ProductResponse;
import com.challenge.market.domain.product.exception.ProductRegistrationFailedException;
import com.challenge.market.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = false)
    public void add(Product product){

        try{
            productRepository.save(product);

        } catch (DataIntegrityViolationException e){
            throw new ProductRegistrationFailedException("DataIntegrityViolationException 으로인한 제품 등록 실패",e);
        }

    }

    public Product get(long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("Product not found " + id ));
    }

        public List<ProductResponse> findAll() {
            return productRepository.findAll()
                    .stream()
                    .map(Product::of)
                .collect(Collectors.toList());
    }

}