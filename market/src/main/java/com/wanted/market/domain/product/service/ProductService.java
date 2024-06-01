package com.wanted.market.domain.product.service;

import com.wanted.market.domain.member.entity.Member;
import com.wanted.market.domain.member.service.MemberService;
import com.wanted.market.domain.product.dto.ProductDto;
import com.wanted.market.domain.product.entity.Product;
import com.wanted.market.domain.product.repository.ProductRepository;
import com.wanted.market.domain.product.request.RegisterRequest;
import com.wanted.market.global.auth.service.SessionUtils;
import com.wanted.market.domain.product.entity.ProductStatusCode;
import com.wanted.market.global.common.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final MemberService memberService;
    private final SessionUtils sessionUtils;

    public long register(RegisterRequest request) {
        Member member = memberService.getMember(sessionUtils.getMemberNo());

        Product product = Product.builder()
                .seller(member)
                .name(request.getName())
                .price(request.getPrice())
                .description(request.getDescription())
                .status(ProductStatusCode.ON_SALE)
                .quantity(request.getQuantity())
                .build();

        productRepository.save(product);
        return product.getProductNo();
    }

    public List<ProductDto> getList(final int page) {
        return productRepository.findAllProducts(PageRequest.of(page, 10)).toList();
    }

    public Product getProduct(final long no) {
        return productRepository.findProductByProductNo(no).orElseThrow(() -> new DataNotFoundException());
    }
}
