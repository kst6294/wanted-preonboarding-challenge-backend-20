package com.wanted.preonboarding.backend20.domain.product.application;

import com.wanted.preonboarding.backend20.domain.member.domain.Member;
import com.wanted.preonboarding.backend20.domain.product.domain.Product;
import com.wanted.preonboarding.backend20.domain.product.dto.ProductDetailDto;
import com.wanted.preonboarding.backend20.domain.product.dto.ProductOutlineDto;
import com.wanted.preonboarding.backend20.domain.product.dto.ProductRequestDto;
import com.wanted.preonboarding.backend20.domain.product.enums.ProductStatus;
import com.wanted.preonboarding.backend20.domain.product.repository.ProductRepository;
import com.wanted.preonboarding.backend20.global.exception.CustomException;
import com.wanted.preonboarding.backend20.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void createProduct(ProductRequestDto dto, Member seller) {
        Product product = Product.builder()
                .dto(dto)
                .seller(seller)
                .build();
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void updateProduct(Long id, ProductRequestDto dto, Member seller) {
        Product product = findProduct(id);
        product.updateProduct(dto);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id, Member seller) {
        Product product = findProduct(id);
        if (!product.getStatus().equals(ProductStatus.SALE)) {
            throw new CustomException(ErrorCode.ONLY_SALES_PRODUCT_CAN_DELETE);
        }
        productRepository.delete(product);
    }

    @Override
    public Page<ProductOutlineDto> searchAllProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(ProductOutlineDto::toProductOutlineDto);
    }

    @Override
    public ProductDetailDto searchProductById(Long id) {
        Product product = findProduct(id);
        return ProductDetailDto.builder()
                .product(product)
                .build();
    }

    public Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }
}
