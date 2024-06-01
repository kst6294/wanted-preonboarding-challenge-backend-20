package com.wanted.preonboarding.domain.product.service;

import com.wanted.preonboarding.domain.product.dto.request.AddProductRequest;
import com.wanted.preonboarding.domain.product.entity.Product;
import com.wanted.preonboarding.domain.repository.ProductRepository;
import com.wanted.preonboarding.domain.user.entity.User;
import com.wanted.preonboarding.domain.user.repository.UserRepository;
import com.wanted.preonboarding.global.exception.entity.RestApiException;
import com.wanted.preonboarding.global.exception.errorCode.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
