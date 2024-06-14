package com.example.wanted.service;

import com.example.wanted.model.Product;
import com.example.wanted.model.State;
import com.example.wanted.model.User;
import com.example.wanted.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void insert(Product product, User user) {
        product.setUser(user);
        product.setP_state(State.ONSALE);
        productRepository.save(product);
    }

    public Page<Product> findAll(String field, String word, Pageable pageable) {
        Page<Product> lists = productRepository.findAll(pageable);
        if (field.equals("title"))
            return productRepository.findByNameContaining(word, pageable);
        if (field.equals("description"))
            return productRepository.findByDescriptionContaining(word, pageable);
        return productRepository.findAll(pageable);
    }

    @Transactional
    public Product findById(Long p_id) {
        Product product = productRepository.findById(p_id).get();
        return product;
    }

    @Transactional
    public void update(Product product) {
        Product newProduct = productRepository.findById(product.getP_id()).get();

        if (product.getStock()==0){
            newProduct.setP_state(State.SOLDOUT);

        }
    }
}
