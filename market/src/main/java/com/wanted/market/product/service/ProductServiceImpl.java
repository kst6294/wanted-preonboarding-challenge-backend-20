package com.wanted.market.product.service;

import com.wanted.market.member.domain.Member;
import com.wanted.market.member.repository.MemberRepository;
import com.wanted.market.order.domain.Order;
import com.wanted.market.order.repository.OrderRepository;
import com.wanted.market.product.domain.Product;
import com.wanted.market.product.dto.ProductRequestDto;
import com.wanted.market.product.dto.ProductResponseDto;
import com.wanted.market.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public ProductServiceImpl(ProductRepository productRepository, MemberRepository memberRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public ProductResponseDto registProduct(ProductRequestDto productRequestDto) {
        Product savedProduct = productRepository.save(productRequestDto.toEntity());
        return ProductResponseDto.createFromEntity(savedProduct);
    }


    @Override
    public ProductResponseDto findById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("제품을 찾을 수 없습니다."));
        return ProductResponseDto.createFromEntity(product);
    }

    @Override
    public List<ProductResponseDto> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductResponseDto::createFromEntity)
                .collect(Collectors.toList());
    }
}
