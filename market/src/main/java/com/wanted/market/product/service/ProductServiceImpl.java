package com.wanted.market.product.service;

import com.wanted.market.member.domain.Member;
import com.wanted.market.member.exception.MemberErrorCode;
import com.wanted.market.member.exception.MemberException;
import com.wanted.market.member.repository.MemberRepository;
import com.wanted.market.order.domain.Order;
import com.wanted.market.order.dto.OrderDetailResponseDto;
import com.wanted.market.order.dto.OrderResponseDto;
import com.wanted.market.order.repository.OrderRepository;
import com.wanted.market.product.domain.Product;
import com.wanted.market.product.dto.ProductDetailResponseDto;
import com.wanted.market.product.dto.ProductRequestDto;
import com.wanted.market.product.dto.ProductResponseDto;
import com.wanted.market.product.exception.ProductErrorCode;
import com.wanted.market.product.exception.ProductException;
import com.wanted.market.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public ProductServiceImpl(ProductRepository productRepository, MemberRepository memberRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
        this.orderRepository = orderRepository;
    }

    // 제품 등록
    @Override
    public ProductResponseDto registerProduct(String username, ProductRequestDto productRequestDto) {
        //판매자
        Member seller = memberRepository.findByEmail(username);
        //판매자 정보와 제품 등록
        Product savedProduct = productRepository.save(productRequestDto.toEntity(seller));
        return ProductResponseDto.createFromEntity(savedProduct);
    }

    
    // 제품 상세 조회
    @Override
    public ProductResponseDto findById(String email, Integer id) {
        // 존재하는 상품인지 확인
        Product findProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductException(ProductErrorCode.NOT_FOUND));

        return ProductResponseDto.createFromEntity(findProduct);
    }

    // 제품 상세 조회, 거래내역 포함
    @Override
    @Transactional
    public ProductDetailResponseDto findDetailProductById(String email, Integer id) {
        // 존재하는 상품인지 확인
        Product findProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductException(ProductErrorCode.NOT_FOUND));

        // 구매자 확인
        Member member = memberRepository.findByEmail(email);
        Member buyer = memberRepository.findById(member.getId())
                .orElseThrow(() -> new ProductException(ProductErrorCode.NOT_FOUND));

        // 구매 내역
        List<OrderDetailResponseDto> orderDetailList = new ArrayList<>();
        
        List<Order> orders = orderRepository.findAllByProductId(id);

        for(Order order : orders) {
            if (order.getMember().getId() == buyer.getId()) {
                orderDetailList.add(OrderDetailResponseDto.createFromEntity(order));
            }
        }
        return ProductDetailResponseDto.builder()
                .product(ProductResponseDto.createFromEntity(findProduct))
                .orderDetailList(orderDetailList)
                .build();
    }

    @Override
    public List<ProductResponseDto> findAll() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(ProductResponseDto::createFromEntity)
                .collect(Collectors.toList());
    }
}
