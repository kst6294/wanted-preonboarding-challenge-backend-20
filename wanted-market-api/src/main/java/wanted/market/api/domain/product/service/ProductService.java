package wanted.market.api.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wanted.market.api.domain.product.dto.internal.ProductInfoDto;
import wanted.market.api.domain.product.dto.request.RegisterProductRequestDto;
import wanted.market.api.domain.product.dto.internal.ProductDto;
import wanted.market.api.domain.product.dto.response.ProductResponseDto;
import wanted.market.api.domain.product.dto.response.RegisterProductResponseDto;
import wanted.market.api.domain.product.entity.Product;
import wanted.market.api.domain.product.enums.Status;
import wanted.market.api.domain.product.repository.ProductRepository;
import wanted.market.api.domain.user.dto.internal.UserInfoDto;
import wanted.market.api.domain.user.entity.User;
import wanted.market.api.domain.user.service.UserService;
import wanted.market.api.global.response.dto.internal.PageInfoDto;
import wanted.market.api.global.response.enums.ExceptionMessage;
import wanted.market.api.global.response.exception.CustomException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final UserService userService;
    private final ProductRepository productRepository;


    public RegisterProductResponseDto registerProduct(RegisterProductRequestDto requestDto) throws CustomException {
        User user = userService.getUser(requestDto.getUserId());
        Product product = Product.builder()
                .name(requestDto.getProductName())
                .price(requestDto.getPrice())
                .count(requestDto.getCount())
                .user(user)
                .build();
        productRepository.save(product);
        return RegisterProductResponseDto.builder().productId(product.getId()).build();
    }

    public ProductDto getProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new CustomException(ExceptionMessage.ISNOTEXIST));
        return ProductDto.builder()
                .product(ProductInfoDto.fromProduct(product))
                .user(UserInfoDto.fromProduct(product))
                .build();
    }

    public ProductResponseDto getAllProducts(int page) {
        if(page<0) throw new CustomException(ExceptionMessage.PAGECANNOTNEGATIVE);
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Order.desc("registerTime")));
        Page<Product> products = productRepository.findAllByStatusNot(Status.COMPLETED, pageable);

        PageInfoDto pageInfoDto = PageInfoDto.fromPage(products);
        List<ProductDto> productDtos = products.stream()
                .map(product ->
                        ProductDto.builder()
                                .product(ProductInfoDto.fromProduct(product))
                                .user(UserInfoDto.fromProduct(product))
                                .build()
                ).toList();

        return ProductResponseDto.builder()
                .page(pageInfoDto)
                .products(productDtos)
                .build();
    }
}