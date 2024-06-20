package com.wanted.market.product.service;

import com.wanted.market.member.domain.Member;
import com.wanted.market.member.exception.MemberErrorCode;
import com.wanted.market.member.exception.MemberException;
import com.wanted.market.member.repository.MemberRepository;
import com.wanted.market.order.domain.Order;
import com.wanted.market.order.dto.OrderDetailResponseDto;
import com.wanted.market.order.model.OrderStatus;
import com.wanted.market.order.repository.OrderRepository;
import com.wanted.market.product.domain.Product;
import com.wanted.market.product.dto.ProductDetailResponseDto;
import com.wanted.market.product.dto.ProductRequestDto;
import com.wanted.market.product.dto.ProductResponseDto;
import com.wanted.market.product.exception.ProductErrorCode;
import com.wanted.market.product.exception.ProductException;
import com.wanted.market.product.model.ProductStatus;
import com.wanted.market.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public ProductServiceImpl(ProductRepository productRepository, MemberRepository memberRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
        this.orderRepository = orderRepository;
    }

    // 제품 등록
    @Override
    public ProductResponseDto registerProduct(String username, ProductRequestDto productRequestDto) {
        // 판매자 정보 조회
        Member seller = memberRepository.findByEmail(username);
        
        // 판매자 정보와 제품 등록
        Product savedProduct = productRepository.save(productRequestDto.toEntity(seller));

        return ProductResponseDto.createFromEntity(savedProduct);
    }

    // 제품 상세 조회
    @Override
    public ProductResponseDto findById(String email, Integer id) {
        // 존재하는 상품인지 확인
        Product findProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductException(ProductErrorCode.NOT_FOUND));

        return ProductResponseDto.createFromEntity(findProduct);
    }

    // 제품 아이디로 상세 조회, 거래내역 포함
    @Override
    @Transactional
    public ProductDetailResponseDto findDetailProductById(String email, Integer id) {
        // 존재하는 상품인지 확인
        Product findProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductException(ProductErrorCode.NOT_FOUND));

        // 구매자 확인
        Member member = memberRepository.findByEmail(email);
        Member buyer = memberRepository.findById(member.getId())
                .orElseThrow(() -> new ProductException(ProductErrorCode.NOT_FOUND));

        // 구매 내역 담을 리스트 생성
        List<OrderDetailResponseDto> orderDetailList = new ArrayList<>();

        // 상품 아이디로 주문 목록 조회
        List<Order> orders = orderRepository.findAllByProductId(id);

        // 사용자 아이디 비교하여 사용자가 구매한 구매내역만 조회
        for(Order order : orders) {
            if (order.getBuyer().getId() == buyer.getId()) {
                orderDetailList.add(OrderDetailResponseDto.createFromEntity(order));
            }
        }

        return ProductDetailResponseDto.builder()
                .product(ProductResponseDto.createFromEntity(findProduct))
                .orderDetailList(orderDetailList)
                .build();
    }

    /* 제품 목록 조회 */
    @Override
    public List<ProductResponseDto> findAll() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(ProductResponseDto::createFromEntity)
                .collect(Collectors.toList());
    }

    /* 내가 구매한 제품 조회 */
    @Override
    public List<ProductResponseDto> findMyProductByMemberId(String email) {
        // 접속한 구매자 확인
        Member buyer = memberRepository.findByEmail(email);
        Integer memberId = buyer.getId();

        // 사용자의 제품리스트 담을 리스트 생성
        List<ProductResponseDto> myProductList = new ArrayList<>();

        // 사용자 아이디로 구매 내역 찾기
        List<Order> orders = orderRepository.findOrderByBuyerId(memberId);

        for (Order order : orders) {
            Product orderdProduct = order.getProduct();
            myProductList.add(ProductResponseDto.createFromEntity(orderdProduct));
        }

        return myProductList;
    }

    /* 사용자가 예약중인 제품 조회 
    *   회원만 가능
    *   구매자, 판매자 가능
    * */
    @Override
    public List<ProductResponseDto> findReservedProduct(String email) throws ProductException{

        // 이메일로 사용자 조회
        Integer memberId = memberRepository.findByEmail(email).getId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND));

        // 판매자, 구매자 주문 내역 담을 리스트 생성
        List<Order> orders = new ArrayList<>();

        // 구매자의 주문내역 조회
        orders.addAll(orderRepository.findAllByBuyerIdAndOrderStatus(memberId, OrderStatus.TRADING));

        // 판매자의 주문내역 조회
        orders.addAll(orderRepository.findAllBySellerIdAndOrderStatus(memberId, OrderStatus.TRADING));

        // 중복 제거
        orders.stream().distinct().collect(Collectors.toList());

        List<ProductResponseDto> reservedProductList = new ArrayList<>();
        for(Order order : orders) {
            Product orderedProduct = order.getProduct();
            // 예약중인 상품만
            if (orderedProduct.getProductStatus() == ProductStatus.RESERVED) {
                reservedProductList.add(ProductResponseDto.createFromEntity(orderedProduct));
            }
        }

        // 예약중인 상품이 없는 경우
        if (reservedProductList.isEmpty()) {
            log.info("findReservedProduct(): 예약중인 상품이 없습니다.");
            throw new ProductException(ProductErrorCode.NO_RESERVED_PRODUCT);
        }

        return reservedProductList;
    }

}
