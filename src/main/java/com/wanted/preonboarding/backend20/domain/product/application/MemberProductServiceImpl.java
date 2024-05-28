package com.wanted.preonboarding.backend20.domain.product.application;

import com.wanted.preonboarding.backend20.domain.member.domain.Member;
import com.wanted.preonboarding.backend20.domain.order.domain.Order;
import com.wanted.preonboarding.backend20.domain.order.enums.OrderStatus;
import com.wanted.preonboarding.backend20.domain.order.repository.OrderRepository;
import com.wanted.preonboarding.backend20.domain.product.domain.Product;
import com.wanted.preonboarding.backend20.domain.product.dto.ProductOutlineDto;
import com.wanted.preonboarding.backend20.domain.product.enums.ProductStatus;
import com.wanted.preonboarding.backend20.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberProductServiceImpl implements MemberProductService{

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Override
    public Page<ProductOutlineDto> searchAllMyProductStatusReserved(Member member, Pageable pageable) {
        Page<Product> reservedProductPage = productRepository.findBySellerAndStatusEquals(member, ProductStatus.RESERVED, pageable);
        return reservedProductPage.map(ProductOutlineDto::toProductOutlineDto);
    }

    @Override
    public Page<ProductOutlineDto> searchAllProductMemberOrder(OrderStatus orderStatus, Member member, Pageable pageable) {
        Page<Order> orderPage = orderRepository.findByBuyerAndBuyerOrderStatus(member, orderStatus, pageable);
        return orderPage.map(order -> {
            ProductOutlineDto dto = ProductOutlineDto.toProductOutlineDto(order.getProduct());
            dto.updateProductStatusAsOrderStatus(order.getBuyerOrderStatus());
            return dto;
        });
    }

}
