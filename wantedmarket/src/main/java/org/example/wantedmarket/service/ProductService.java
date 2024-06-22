package org.example.wantedmarket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.wantedmarket.dto.order.TransactionResponse;
import org.example.wantedmarket.dto.product.*;
import org.example.wantedmarket.exception.CustomException;
import org.example.wantedmarket.exception.ErrorCode;
import org.example.wantedmarket.domain.Order;
import org.example.wantedmarket.domain.Product;
import org.example.wantedmarket.domain.User;
import org.example.wantedmarket.repository.jpa.OrderJpaRepository;
import org.example.wantedmarket.repository.ProductRepository;
import org.example.wantedmarket.repository.jpa.UserJpaRepository;
import org.example.wantedmarket.status.OrderStatus;
import org.example.wantedmarket.status.ProductStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserJpaRepository userRepository;
    private final OrderJpaRepository orderRepository;

    /* 제품 등록 */
    @Transactional
    public ProductResponse saveProduct(Long userId, ProductCreateRequest request) {
        User seller = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 재고가 1개 이상일 때만 제품 등록을 할 수 있음
        if (request.getQuantity() < 1) {
            throw new CustomException(ErrorCode.PRODUCT_NOT_ENOUGH);
        }

        Product newProduct = productRepository.save(Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .status(ProductStatus.FOR_SALE)
                .quantity(request.getQuantity())
                .seller(seller)
                .build());

        return ProductResponse.from(newProduct);
    }

    /* 제품 전체 목록 조회 */
    @Transactional(readOnly = true)
    public List<ProductResponse> findAllProductList() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }

    /* 제품 상세 조회  without 거래내역 */
    @Transactional(readOnly = true)
    public ProductResponse findDetailProduct(Long productId) {
        Product findProduct = productRepository.findById(productId).orElseThrow(
                () ->  new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        return ProductResponse.from(findProduct);
    }

    /* 제품 상세 조회 with 거래내역 */
    @Transactional(readOnly = true)
    public ProductDetailResponse findDetailProductWithTransaction(Long userId, Long productId) {
        Product findProduct = productRepository.findById(productId).orElseThrow(
                () ->   new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        List<TransactionResponse> transactionList = new ArrayList<>();

        List<Order> orders = orderRepository.findAllByProductId(productId);
        for(Order order : orders) {
            if (order.getSeller().getId().equals(userId) || order.getBuyer().getId().equals(userId)) {
                transactionList.add(TransactionResponse.from(order));
            }
        }

        return ProductDetailResponse.builder()
                .product(ProductResponse.from(findProduct))
                .transactions(transactionList)
                .build();
    }

    /* 구매한 제품 목록 조회 */
    @Transactional(readOnly = true)
    public List<ProductResponse> findOrderedProductList(Long userId) {
        List<Order> orders = orderRepository.findAllByBuyerIdAndStatus(userId, OrderStatus.CONFIRMED);
        List<ProductResponse> orderedProductList = new ArrayList<>();

        for (Order order : orders) {
            Product orderdProduct = order.getProduct();
            orderedProductList.add(ProductResponse.from(orderdProduct));
        }

        return orderedProductList;
    }

    /* 내가 등록한 제품 조회 */
    public List<ProductResponse> findMyProductList(Long userId) {
        return productRepository.findAllBySellerId(userId)
                .stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }

    /* 예약중인 제품 목록 조회
    *
    *  조회하는 사람이 판매자이든 구매자이든 상관없이 예약중인 제품이면 확인 가능
    */
    @Transactional(readOnly = true)
    public List<ProductResponse> findReservedProductList(Long userId) {
        List<Order> orders = new ArrayList<>();
        orders.addAll(orderRepository.findAllByBuyerIdAndStatus(userId, OrderStatus.PENDING));
        orders.addAll(orderRepository.findAllByBuyerIdAndStatus(userId, OrderStatus.APPROVED));
        orders.addAll(orderRepository.findAllBySellerIdAndStatus(userId, OrderStatus.PENDING));
        orders.addAll(orderRepository.findAllBySellerIdAndStatus(userId, OrderStatus.APPROVED));
        orders.stream().distinct().collect(Collectors.toList());

        List<ProductResponse> reservedProductList = new ArrayList<>();

        for (Order order : orders) {
            Product reservedProduct = order.getProduct();
            reservedProductList.add(ProductResponse.from(reservedProduct));
        }

        return reservedProductList;
    }

    /* 제품 가격 수정 */
    @Transactional
    public ProductResponse modifyProductPrice(Long userId, ProductUpdateRequest request) {
        Product product = productRepository.findById(request.getId()).orElseThrow(
                () -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        if (!product.getSeller().getId().equals(userId)) {
            throw new CustomException(ErrorCode.USER_NOT_SELLER);
        }

        if (product.getPrice().equals(request.getPrice())) {
            throw new CustomException(ErrorCode.SAME_AS_PREVIOUS_PRICE);
        }

        // SOLD_OUT 제품은 가격을 수정할 필요가 없음
        if (product.getStatus().equals(ProductStatus.SOLD_OUT)) {
            throw new CustomException(ErrorCode.PRODUCT_SOLD_OUT);
        }

        product.modifyPrice(request.getPrice());

        return ProductResponse.from(product);
    }

}
