package wanted.market.api.domain.product.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.market.api.domain.product.dto.internal.ProductInfoDto;
import wanted.market.api.domain.product.dto.request.ModifiedProductRequestDto;
import wanted.market.api.domain.product.dto.request.RegisterProductRequestDto;
import wanted.market.api.domain.product.dto.response.ModifiedProductResponseDto;
import wanted.market.api.domain.product.dto.response.ProductDetailResponseDto;
import wanted.market.api.domain.product.dto.response.ProductPageResponseDto;
import wanted.market.api.domain.product.dto.response.RegisterProductResponseDto;
import wanted.market.api.domain.product.entity.Product;
import wanted.market.api.domain.product.enums.ProductStatus;
import wanted.market.api.domain.product.repository.ProductRepository;
import wanted.market.api.domain.user.dto.internal.UserInfoDto;
import wanted.market.api.domain.user.entity.User;
import wanted.market.api.domain.user.service.UserService;
import wanted.market.api.global.log.LogBuilder;
import wanted.market.api.global.response.dto.internal.PageInfoDto;
import wanted.market.api.global.response.enums.ExceptionDomain;
import wanted.market.api.global.response.enums.ExceptionMessage;
import wanted.market.api.global.response.exception.WantedException;

import java.util.List;

import static wanted.market.api.domain.product.constants.ProductConstant.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final LogBuilder logBuilder;
    private final UserService userService;
    private final ProductRepository productRepository;

    public RegisterProductResponseDto registerProduct(HttpServletRequest request, RegisterProductRequestDto requestDto) throws WantedException {
        User user = userService.getUser(request);
        Product product = Product.builder()
                .name(requestDto.getProductName())
                .price(requestDto.getPrice())
                .count(requestDto.getCount())
                .user(user)
                .build();
        productRepository.save(product);
        return RegisterProductResponseDto.builder().productId(product.getId()).build();
    }

    public ProductDetailResponseDto searchProductDetail(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new WantedException(ExceptionDomain.PRODUCT, ExceptionMessage.IS_NOT_EXIST));


        return ProductDetailResponseDto.builder()
                .product(ProductInfoDto.fromProductAndUser(product, UserInfoDto.fromProduct(product)))
                .build();
    }

    public ProductPageResponseDto searchProductList(int page, String status) {
        if (page < 0) page = DEFAULT_PAGE_NUMBER;
        ProductStatus productStatus = ProductStatus.addDefaultValueOf(status.toUpperCase());
        Pageable pageable = PageRequest.of(page-1, DEFAULT_PAGE_SIZE, Sort.by(Sort.Order.desc(DEFAULT_ORDER_CRITERIA)));
        Page<Product> products = productRepository.findAllByStatus(productStatus, pageable);

        PageInfoDto pageInfoDto = PageInfoDto.fromPage(products);
        List<ProductInfoDto> productInfoDtos = products.stream()
                .map(product ->
                        ProductInfoDto.fromProductAndUser(product, UserInfoDto.fromProduct(product))
                ).toList();

        return ProductPageResponseDto.builder()
                .page(pageInfoDto)
                .products(productInfoDtos)
                .build();
    }

    @Transactional
    public ModifiedProductResponseDto modifyProduct(HttpServletRequest request, ModifiedProductRequestDto requestDto) {
        User user = userService.getUser(request);
        Product product = productRepository.findById(requestDto.getProductId()).orElseThrow(() -> new WantedException(ExceptionDomain.PRODUCT, ExceptionMessage.IS_NOT_EXIST));
        if (!product.getUser().equals(user)) throw new WantedException(ExceptionDomain.USER, ExceptionMessage.IS_NOT_OWNER);
        if(!product.getStatus().equals(ProductStatus.SALE)) throw new WantedException(ExceptionDomain.PRODUCT, ExceptionMessage.IS_NOT_ON_SALE);
        long beforePrice = product.getPrice();
        long afterPrice = requestDto.getPrice();
        product.modifiedPrice(afterPrice);
        log.info(logBuilder.modifiedPriceLog(product.getId(), beforePrice, afterPrice));
        return ModifiedProductResponseDto.builder()
                .productId(product.getId())
                .beforePrice(beforePrice)
                .afterPrice(afterPrice)
                .build();
    }
}