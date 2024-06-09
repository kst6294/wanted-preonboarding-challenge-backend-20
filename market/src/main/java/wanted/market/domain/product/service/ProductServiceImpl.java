package wanted.market.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wanted.market.domain.member.repository.MemberRepository;
import wanted.market.domain.member.repository.entity.Member;
import wanted.market.domain.product.repository.ProductRepository;
import wanted.market.domain.product.repository.entity.Product;
import wanted.market.domain.product.service.dto.request.ProductRegisterServiceRequest;
import wanted.market.domain.product.service.dto.response.ProductDetailResponse;
import wanted.market.domain.product.service.dto.response.ProductListResponse;
import wanted.market.global.exception.CommonErrorCode;
import wanted.market.global.exception.RestApiException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final int pageLimit = 3;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Override
    public Long register(ProductRegisterServiceRequest request) {
        Member member = memberRepository.findMemberByEmail(request.getEmail())
                .orElseThrow(() -> new RestApiException(CommonErrorCode.USER_NOT_FOUND));

        return productRepository.save(request.toProduct(member)).getId();
    }

    @Override
    public List<ProductListResponse> findProductList(int page) {
        Page<Product> products = productRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        return products.stream()
                .map(ProductListResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDetailResponse findProductDetail(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.DATA_NOT_FOUND));

        return ProductDetailResponse.of(product);
    }

    @Override
    public List<ProductListResponse> findProductWithMember(String email) {
        Member member = memberRepository.findMemberByEmail(email)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.USER_NOT_FOUND));

        List<Product> products = productRepository.findAllByMember(member);

        return products.stream()
                .map(ProductListResponse::of)
                .collect(Collectors.toList());
    }
}
