package com.example.wanted_market.service;

import com.example.wanted_market.domain.Product;
import com.example.wanted_market.domain.User;
import com.example.wanted_market.dto.request.ProductRegesterRequestDto;
import com.example.wanted_market.dto.response.ProductDetailResponseDto;
import com.example.wanted_market.dto.response.ProductDetailResponseDto.ProductDetailDto;
import com.example.wanted_market.dto.response.ProductDetailResponseDto.TransactionDto;
import com.example.wanted_market.dto.response.ProductResponseDto;
import com.example.wanted_market.repository.OrderRepository;
import com.example.wanted_market.repository.ProductRepository;
import com.example.wanted_market.repository.UserRepository;
import com.example.wanted_market.type.EProductStatus;
import com.example.wanted_market.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> { throw new IllegalArgumentException("해당 사용자가 존재하지 않습니다."); });
    }

    @Transactional(readOnly = true)
    public Product getProductById(Long productId) {
        return productRepository.findProductById(productId)
                .orElseThrow(() -> { throw new IllegalArgumentException("해당 제품이 존재하지 않습니다."); });
    }

    // 제품 등록
    @Transactional
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
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getList() {
        List<ProductResponseDto> productList = productRepository.findAllProducts().stream()
                .map(p -> new ProductResponseDto(
                        p.getId(),
                        p.getName(),
                        p.getPrice(),
                        p.getStatus()
                )).collect(Collectors.toList());

        return productList;
    }

    // 제품 상세 조회
    @Transactional(readOnly = true)
    public ProductDetailResponseDto getDetail(Long productId) {
        Product product = getProductById(productId);

        ProductDetailDto productDetail =
                ProductDetailDto.builder()
                        .name(product.getName())
                        .price(product.getPrice())
                        .status(product.getStatus())
                        .sellerNickname(product.getSeller().getNickname())
                        .build();

        List<TransactionDto> transaction = orderRepository.findByProductId(productId).stream()
                .map(order -> TransactionDto.builder()
                        .orderId(order.getId())
                        .buyerNickname(order.getBuyer().getNickname())
                        .orderStatus(order.getStatus())
                        .orderTime(DateUtil.formatDateTime(order.getCreateDate()))
                        .build()
                ).collect(Collectors.toList());

        ProductDetailResponseDto productDetailResponse =
                ProductDetailResponseDto.builder()
                        .productDetail(productDetail)
                        .transactions(transaction)
                        .build();
        
        return productDetailResponse;
    }
}
