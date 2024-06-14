package io.taylor.wantedpreonboardingchallengebackend20.product.service;

import io.taylor.wantedpreonboardingchallengebackend20.common.util.JwtTokenUtil;
import io.taylor.wantedpreonboardingchallengebackend20.product.model.request.ProductRequest;
import io.taylor.wantedpreonboardingchallengebackend20.product.model.response.ProductResponse;
import lombok.extern.slf4j.Slf4j;
import io.taylor.wantedpreonboardingchallengebackend20.order.service.OrderService;
import io.taylor.wantedpreonboardingchallengebackend20.product.entity.Product;
import io.taylor.wantedpreonboardingchallengebackend20.product.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
public class ProductService {
    private final JwtTokenUtil jwtTokenUtil;
    private final ProductRepository productRepository;
    private final OrderService orderService;

    public ProductService(JwtTokenUtil jwtTokenUtil, ProductRepository productRepository, OrderService orderService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.productRepository = productRepository;
        this.orderService = orderService;
    }

    public List<Product> getProducts() {
        List<Product> productList = productRepository.findAll();
        if (productList.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "등록된 상품이 없습니다.");
        return productList;
    }

    public ProductResponse postProduct(String authorization, ProductRequest request) {
        String userId = jwtTokenUtil.getUserIdFromToken(authorization);
        if (userId.isEmpty()) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "존재하지 않는 회원입니다.");

        Product product = productRepository.save(new Product(request.name(), request.price()));
        if (product == null) throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "상품 등록에 실패했습니다.");
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getStatus(), product.getUpdatedAt(), product.getCreatedAt());
    }

    public ProductResponse getProductById(long productId) {
        Product product = productRepository.findById(productId);
        if (product == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 상품이 존재하지 않습니다.");
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getStatus(), product.getUpdatedAt(), product.getCreatedAt());
    }
}
