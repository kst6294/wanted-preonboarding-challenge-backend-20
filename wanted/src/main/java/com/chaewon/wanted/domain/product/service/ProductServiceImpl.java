package com.chaewon.wanted.domain.product.service;

import com.chaewon.wanted.domain.member.entity.Member;
import com.chaewon.wanted.domain.member.exception.MemberNotFoundException;
import com.chaewon.wanted.domain.member.repository.MemberRepository;
import com.chaewon.wanted.domain.product.dto.request.ModifyRequestDto;
import com.chaewon.wanted.domain.product.dto.response.ProductDetailResponseDto;
import com.chaewon.wanted.domain.product.dto.response.ProductResponseDto;
import com.chaewon.wanted.domain.product.dto.request.RegisterRequestDto;
import com.chaewon.wanted.domain.product.entity.Product;
import com.chaewon.wanted.domain.product.exception.DuplicatePriceException;
import com.chaewon.wanted.domain.product.exception.ProductNotFoundException;
import com.chaewon.wanted.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void registerProduct(String email, RegisterRequestDto registrationDto) {
        Member member = getByEmail(email);
        Product product = registrationDto.toEntity(registrationDto, member);
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void modifyProduct(String email, Long productId, ModifyRequestDto modifyRequestDto) {
        Product product = getByProduct(productId);

        if (!product.getMember().equals(getByEmail(email))) {
            throw new MemberNotFoundException("판매자가 아닙니다.");
        }

        if (product.getPrice() == modifyRequestDto.getPrice()) {
            throw new DuplicatePriceException("수정 전 가격과 동일합니다.");
        }
        product.modifyProduct(modifyRequestDto.getPrice());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDto> listProducts(Pageable pageable) {
        Page<Product> productList = productRepository.findAll(pageable);

        return productList.map(ProductResponseDto::from);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDetailResponseDto getProductDetails(Long productId) {
        return productRepository.findById(productId)
                .map(ProductDetailResponseDto::from)
                .orElseThrow(() -> new ProductNotFoundException("해당 제품을 찾을 수 없습니다. 제품 ID: " + productId));
    }

    private Member getByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("해당 멤버를 찾을 수 없습니다."));
    }

    private Product getByProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("해당 제품을 찾을 수 없습니다. 제품 ID: " + productId));
    }
}
