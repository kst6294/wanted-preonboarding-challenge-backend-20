package com.example.wanted_market.service;

import com.example.wanted_market.domain.Product;
import com.example.wanted_market.domain.User;
import com.example.wanted_market.dto.request.ProductRegesterRequestDto;
import com.example.wanted_market.dto.response.ProductResponseDto;
import com.example.wanted_market.repository.ProductRepository;
import com.example.wanted_market.repository.UserRepository;
import com.example.wanted_market.type.EProductStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> { throw new IllegalArgumentException("해당 사용자가 존재하지 않습니다."); });
    }

    // 제품 등록
    public void register(Long userId, ProductRegesterRequestDto productRegesterRequestDto) {
        User user = getUserById(userId);

        productRepository.save(Product.builder()
                .seller(user)
                .name(productRegesterRequestDto.name())
                .price(productRegesterRequestDto.price())
                .status(EProductStatus.FOR_SALE)
                .build());
    }

    // 제품 목록 조회
    public List<ProductResponseDto> getList() {
        List<ProductResponseDto> productList = productRepository.findAllProductDetails().stream()
                .map(p -> new ProductResponseDto(
                        p.getId(),
                        p.getName(),
                        p.getPrice(),
                        p.getStatus()
                )).collect(Collectors.toList());

        return productList;
    }
}
