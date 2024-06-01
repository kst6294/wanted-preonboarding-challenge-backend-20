package com.wanted.preonboarding.domain.product.service;

import com.wanted.preonboarding.domain.product.dto.request.AddProductRequest;
import com.wanted.preonboarding.domain.product.dto.response.ProductResponse;
import com.wanted.preonboarding.domain.product.entity.Product;
import com.wanted.preonboarding.domain.product.repository.ProductRepository;
import com.wanted.preonboarding.domain.user.entity.User;
import com.wanted.preonboarding.domain.user.repository.UserRepository;
import com.wanted.preonboarding.global.exception.entity.RestApiException;
import com.wanted.preonboarding.global.exception.errorCode.ProductErrorCode;
import com.wanted.preonboarding.global.exception.errorCode.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void addProduct(Long userId, AddProductRequest addProductRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RestApiException(UserErrorCode.USER_NOT_FOUND, "유저를 찾을 수 없습니다. 아이디: "+userId));
        productRepository.save(addProductRequest.toEntity(user));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getProducts() {
        List<Product> productList = productRepository.findAll();
        List<ProductResponse> productResponseList = new ArrayList<>();
        for(Product product : productList) productResponseList.add(ProductResponse.of(product));
        return productResponseList;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RestApiException(ProductErrorCode.PRODUCT_NOT_FOUND, "상품을 찾을 수 없습니다. 아이디: "+productId));
        return ProductResponse.of(product);
    }
}
