package com.wanted.market.product.service;

import com.wanted.market.global.exception.ProductNotFoundException;
import com.wanted.market.product.domain.Product;
import com.wanted.market.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public List<Product> getProductList() {
        return productRepository.findAll();
    }

    public Product addProduct(Product product){
        return productRepository.save(product);
    }

    public Product getProduct(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ProductNotFoundException());
        return product;
    }

    public List<Product> getPurchasedProductListByBuyerId(Long memberId) {
        return productRepository.findPurchasedProductsByBuyerId(memberId);
    }

    public List<Product> getReservedProductsByMemberId(Long memberId) {
        return productRepository.findReservedProductsByMemberId(memberId);
    }

    public List<Product> getProductsBySellerIdAndBuyerId(Long sellerId, Long buyerId){
        return productRepository.findProductsBySellerIdAndBuyerId(sellerId, buyerId);
    }

    public void deleteProductById(Long productId){
        productRepository.deleteById(productId);
    }
}
