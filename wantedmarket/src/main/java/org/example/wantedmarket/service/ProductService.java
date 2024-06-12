package org.example.wantedmarket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.wantedmarket.dto.order.OrderInfoDto;
import org.example.wantedmarket.dto.product.ProductCreateDto;
import org.example.wantedmarket.dto.product.ProductDetailDto;
import org.example.wantedmarket.dto.product.ProductInfoDto;
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

        Product newProduct = productRepository.save(Product.builder()
                        .name(request.getName())
                        .price(request.getPrice())
                        .status(ProductStatus.FOR_SALE)
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
        if (findProduct.getSeller().getId() == userId) {
            transactionList = orderRepository.findAllBySellerId(userId).stream()
                    .map(order -> new OrderInfoDto(
                            order.getId(),
                            order.getProduct().getId(),
                            order.getSeller().getId(),
                            order.getBuyer().getId(),
                            order.getStatus()
                    )).collect(Collectors.toList());
        }

        List<Order> orders = orderRepository.findAllByProductId(findProduct.getId());
        for (Order order : orders) {
            if (order.getBuyer().getId() == userId) {
                transactionList.add(new OrderInfoDto(
                        order.getId(),
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
                .status(findProduct.getStatus())
                .sellerId(findProduct.getSeller().getId())
                .transactionList(transactionList)
                .build();
    }

    /* 구매한 제품 목록 조회 */
    @Transactional(readOnly = true)
    public List<ProductInfoDto> findOrderedProductList(Long userId) {
        List<Order> orders = orderRepository.findAllByBuyerIdAndStatus(userId, OrderStatus.APPROVE);

        List<ProductInfoDto> orderedProductList = new ArrayList<>();

        for (Order order : orders) {
            log.info("order : " + order.getProduct().getName() + ", " + order.getStatus() + ", seller - " + order.getSeller().getId() + ", buyer - " + order.getBuyer().getId());

            ProductInfoDto productInfoDto = new ProductInfoDto();
            productInfoDto.setProductId(order.getProduct().getId());
            productInfoDto.setName(order.getProduct().getName());
            productInfoDto.setPrice(order.getProduct().getPrice());
            productInfoDto.setStatus(order.getProduct().getStatus());

            orderedProductList.add(productInfoDto);
            log.info("productInfoDto : " + productInfoDto.getName() + ", " + productInfoDto.getStatus());
        }
        return orderedProductList;
    }

    /* 예약중인 제품 목록 조회 */
    @Transactional(readOnly = true)
    public List<ProductInfoDto> findReservedProductList(Long userId) {
        List<Order> orders = orderRepository.findAllByBuyerIdAndStatus(userId, OrderStatus.IN_RESERVATION);
        orders.addAll(orderRepository.findAllBySellerIdAndStatus(userId, OrderStatus.IN_RESERVATION));
        orders.stream().distinct().collect(Collectors.toList());

        List<ProductInfoDto> reservedProductList = new ArrayList<>();

        for (Order order : orders) {
            log.info("order : " + order.getProduct().getName() + ", " + order.getStatus() + ", seller - " + order.getSeller().getId() + ", buyer - " + order.getBuyer().getId());

            ProductInfoDto productInfoDto = new ProductInfoDto();
            productInfoDto.setProductId(order.getProduct().getId());
            productInfoDto.setName(order.getProduct().getName());
            productInfoDto.setPrice(order.getProduct().getPrice());
            productInfoDto.setStatus(order.getProduct().getStatus());

            reservedProductList.add(productInfoDto);
            log.info("productInfoDto : " + productInfoDto.getName() + ", " + productInfoDto.getStatus());
        }
        return reservedProductList;
    }

}
