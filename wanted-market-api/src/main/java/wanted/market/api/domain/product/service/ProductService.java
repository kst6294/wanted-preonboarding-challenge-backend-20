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

    public RegisterProductResponseDto registerProduct(HttpServletRequest request, RegisterProductRequestDto requestDto) {
        User user = userService.getUser(request);
        Product product = Product.of(requestDto.getProductName(), requestDto.getPrice(), requestDto.getCount(), user);
        productRepository.save(product);
        return RegisterProductResponseDto.of(product.getId());
    }

    public ProductDetailResponseDto searchProductDetail(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new WantedException(ExceptionDomain.PRODUCT, ExceptionMessage.IS_NOT_EXIST));

        UserInfoDto userInfoDto = UserInfoDto.from(product);
        return ProductDetailResponseDto.of(ProductInfoDto.from(product));
    }

    public ProductPageResponseDto searchProductList(Pageable pageable, String status) {
        ProductStatus productStatus = ProductStatus.addDefaultValueOf(status.toUpperCase());
        pageable = PageRequest.of(pageable.getPageNumber(), DEFAULT_PAGE_SIZE, Sort.by(Sort.Order.desc(DEFAULT_ORDER_CRITERIA)));
        Page<Product> products = productRepository.findAllByStatus(productStatus, pageable);

        PageInfoDto pageInfoDto = PageInfoDto.fromPage(products);
        List<ProductInfoDto> productInfoDtos = products.stream()
                .map(ProductInfoDto::from)
                .toList();

        return ProductPageResponseDto.of(pageInfoDto, productInfoDtos);
    }

    @Transactional
    public ModifiedProductResponseDto modifyProduct(HttpServletRequest request, ModifiedProductRequestDto requestDto) {
        User user = userService.getUser(request);
        Product product = productRepository.findById(requestDto.getProductId()).orElseThrow(() -> new WantedException(ExceptionDomain.PRODUCT, ExceptionMessage.IS_NOT_EXIST));
        if (!product.getUser().equals(user)) {
            throw new WantedException(ExceptionDomain.PRODUCT, ExceptionMessage.IS_NOT_OWNER);
        }
        if (!product.getStatus().equals(ProductStatus.SALE)) {
            throw new WantedException(ExceptionDomain.PRODUCT, ExceptionMessage.IS_NOT_ON_SALE);
        }
        long beforePrice = product.getPrice();
        long afterPrice = requestDto.getPrice();
        product.modifiedPrice(afterPrice);
        log.info(logBuilder.modifiedPriceLog(product.getId(), beforePrice, afterPrice));
        return ModifiedProductResponseDto.of(product.getId(), beforePrice, afterPrice);
    }
}