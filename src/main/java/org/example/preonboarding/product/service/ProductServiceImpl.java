package org.example.preonboarding.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.preonboarding.member.model.entity.Member;
import org.example.preonboarding.member.repository.MemberRepository;
import org.example.preonboarding.member.util.MemberUtil;
import org.example.preonboarding.product.exception.ProductNotFoundException;
import org.example.preonboarding.product.model.entity.Product;
import org.example.preonboarding.product.model.mapper.ProductMapper;
import org.example.preonboarding.product.model.payload.request.ProductCreateRequest;
import org.example.preonboarding.product.model.payload.response.ProductResponse;
import org.example.preonboarding.product.repository.ProductRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.example.preonboarding.product.model.enums.ProductSellingStatus.SELLING;
import static org.example.preonboarding.product.model.enums.ProductType.UNCLASSIFIED;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final MemberUtil memberUtil;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final ProductNumberFactory productNumberFactory;

    @Override
    public List<ProductResponse> getProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductMapper.INSTANCE::toProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getProductsByUser() {
        Member currentUser = memberUtil.getCurrentUser();
        List<Product> myProducts = productRepository.findAllBySeller(currentUser);
        return myProducts.stream().map(ProductMapper.INSTANCE::toProductResponse).toList();
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new RuntimeException("제품이 없습니다.")
        );
        return ProductMapper.INSTANCE.toProductResponse(product);
    }

    @Override
    public ProductResponse createProduct(ProductCreateRequest productCreateRequest) {

        Member member = memberRepository.findByUserId(productCreateRequest.getSellingUserId()).orElseThrow(() ->
                new UsernameNotFoundException("유효하지 않은 회원입니다.")
        );

        String productNumber = productNumberFactory.createNextProductNumber();
        Product product = Product.builder()
                .productNumber(productNumber)
                .productType(UNCLASSIFIED)
                .productSellingStatus(SELLING)
                .price(productCreateRequest.getPrice())
                .name(productCreateRequest.getName())
                .description(productCreateRequest.getDescription())
                .seller(member)
                .build();

        Product savedProduct = productRepository.save(product);

        return ProductMapper.INSTANCE.toProductResponse(savedProduct);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long productId, ProductCreateRequest productCreateRequest) {
        // TODO
        return null;
    }

    @Override
    public ProductResponse deleteProduct(Long productId) throws ProductNotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new ProductNotFoundException("해당 id를 가진 제품이 없습니다.")
        );
        // TODO : soft delete로 변경
        productRepository.deleteById(productId);

        return ProductResponse.builder()
                .build();
    }
}
