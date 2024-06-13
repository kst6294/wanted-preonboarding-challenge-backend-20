package org.example.wantedmarket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.wantedmarket.dto.order.OrderInfoDto;
import org.example.wantedmarket.dto.product.ProductCreateDto;
import org.example.wantedmarket.dto.product.ProductDetailDto;
import org.example.wantedmarket.dto.product.ProductInfoDto;
import org.example.wantedmarket.dto.product.ProductUpdateDto;
import org.example.wantedmarket.error.CustomException;
import org.example.wantedmarket.error.ErrorCode;
import org.example.wantedmarket.model.Order;
import org.example.wantedmarket.model.Product;
import org.example.wantedmarket.model.User;
import org.example.wantedmarket.repository.OrderRepository;
import org.example.wantedmarket.repository.ProductRepository;
import org.example.wantedmarket.repository.UserRepository;
import org.example.wantedmarket.status.OrderStatus;
import org.example.wantedmarket.status.ProductStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    /* 제품 등록 */
    @Transactional
    public ProductCreateDto.Response saveProduct(Long userId, ProductCreateDto.Request request) {
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

        return ProductCreateDto.Response.from(newProduct);
    }

    /* 제품 전체 목록 조회 */
    @Transactional(readOnly = true)
    public List<ProductInfoDto> findAllProductList() {
        return productRepository.findAll().stream()
                .map(product -> new ProductInfoDto(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getQuantity(),
                        product.getStatus()
                )).collect(Collectors.toList());
    }

    /* 제품 상세 조회 */
    @Transactional(readOnly = true)
    public ProductDetailDto findDetailProduct(Long productId) {
        Product findProduct = productRepository.findById(productId).orElseThrow(
                () ->  new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        return ProductDetailDto.builder()
                .productId(findProduct.getId())
                .name(findProduct.getName())
                .price(findProduct.getPrice())
                .quantity(findProduct.getQuantity())
                .status(findProduct.getStatus())
                .sellerId(findProduct.getSeller().getId())
                .build();
    }

    /* 제품 상세 조회 with 거래내역 */
    @Transactional(readOnly = true)
    public ProductDetailDto findDetailProductWithTransaction(Long userId, Long productId) {
        Product findProduct = productRepository.findById(productId).orElseThrow(
                () ->   new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        List<OrderInfoDto> transactionList = new ArrayList<>();

        // 조회하는 사람이 제품의 판매자일 때
        if (findProduct.getSeller().getId().equals(userId)) {
            transactionList = orderRepository.findAllBySellerId(userId).stream()
                    .map(order -> new OrderInfoDto(
                            order.getId(),
                            order.getConfirmedPrice(),
                            order.getQuantity(),
                            order.getProduct().getId(),
                            order.getSeller().getId(),
                            order.getBuyer().getId(),
                            order.getStatus()
                    )).collect(Collectors.toList());
        }

        List<Order> orders = orderRepository.findAllByProductId(findProduct.getId());

        for (Order order : orders) {
            // 조회하는 사람이 제품의 구매자일 때
            if (order.getBuyer().getId().equals(userId)) {
                transactionList.add(new OrderInfoDto(
                        order.getId(),
                        order.getConfirmedPrice(),
                        order.getQuantity(),
                        order.getProduct().getId(),
                        order.getSeller().getId(),
                        order.getBuyer().getId(),
                        order.getStatus()
                ));
            }
        }

        return ProductDetailDto.builder()
                .productId(findProduct.getId())
                .name(findProduct.getName())
                .price(findProduct.getPrice())
                .quantity(findProduct.getQuantity())
                .status(findProduct.getStatus())
                .sellerId(findProduct.getSeller().getId())
                .transactionList(transactionList)
                .build();
    }

    /* 구매한 제품 목록 조회 */
    @Transactional(readOnly = true)
    public List<ProductInfoDto> findOrderedProductList(Long userId) {
        List<Order> orders = orderRepository.findAllByBuyerIdAndStatus(userId, OrderStatus.CONFIRMED);

        List<ProductInfoDto> orderedProductList = new ArrayList<>();

        for (Order order : orders) {
            log.info(order.toString());

            Product orderdProduct = order.getProduct();
            ProductInfoDto productInfoDto = ProductInfoDto.builder()
                    .productId(orderdProduct.getId())
                    .name(orderdProduct.getName())
                    .price(order.getConfirmedPrice())
                    .quantity(order.getQuantity())
                    .status(orderdProduct.getStatus())
                    .build();

            orderedProductList.add(productInfoDto);
            log.info(productInfoDto.toString());
        }

        return orderedProductList;
    }

    /* 예약중인 제품 목록 조회
    *
    *  조회하는 사람이 판매자이든 구매자이든 상관없이 예약중인 제품이면 확인 가능
    */
    @Transactional(readOnly = true)
    public List<ProductInfoDto> findReservedProductList(Long userId) {
        List<Order> orders = new ArrayList<>();
        orders.addAll(orderRepository.findAllByBuyerIdAndStatus(userId, OrderStatus.PENDING));
        orders.addAll(orderRepository.findAllByBuyerIdAndStatus(userId, OrderStatus.APPROVED));
        orders.addAll(orderRepository.findAllBySellerIdAndStatus(userId, OrderStatus.PENDING));
        orders.addAll(orderRepository.findAllBySellerIdAndStatus(userId, OrderStatus.APPROVED));
        orders.stream().distinct().collect(Collectors.toList());

        List<ProductInfoDto> reservedProductList = new ArrayList<>();

        for (Order order : orders) {
            log.info(order.toString());

            Product reservedProduct = order.getProduct();
            ProductInfoDto productInfoDto = ProductInfoDto.builder()
                    .productId(reservedProduct.getId())
                    .name(reservedProduct.getName())
                    .price(order.getConfirmedPrice())
                    .quantity(order.getQuantity())
                    .status(reservedProduct.getStatus())
                    .build();

            reservedProductList.add(productInfoDto);
            log.info(productInfoDto.toString());
        }
        return reservedProductList;
    }

    /* 제품 가격 수정 */
    @Transactional
    public ProductUpdateDto.Response modifyProductPrice(Long userId, ProductUpdateDto.Request request) {
        Product product = productRepository.findById(request.getProductId()).orElseThrow(
                () -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        if (!product.getSeller().getId().equals(userId)) {
            throw  new CustomException(ErrorCode.USER_NOT_SELLER);
        }

        product.modifyPrice(request.getPrice());

        return ProductUpdateDto.Response.from(product);
    }

}
