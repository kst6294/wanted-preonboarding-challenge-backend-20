package com.wanted.service;

import com.wanted.dto.MemberDto;
import com.wanted.dto.OrderDto;
import com.wanted.dto.ProductDto;
import com.wanted.entity.Member;
import com.wanted.entity.Order;
import com.wanted.entity.OrderProduct;
import com.wanted.entity.Product;
import com.wanted.exception.MemberException;
import com.wanted.exception.ProductException;
import com.wanted.exception.SessionNotFoundException;
import com.wanted.repository.MemberRepository;
import com.wanted.repository.OrderProductRepository;
import com.wanted.repository.OrderRepository;
import com.wanted.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final OrderProductRepository orderProductRepository;
    private final OrderRepository orderRepository;

    /* 제품 등록 */
    @Transactional
    public ProductDto join(ProductDto productDto, HttpSession session) {

        MemberDto memberDto = (MemberDto) session.getAttribute("memberDto");
        if(memberDto == null){
            throw new SessionNotFoundException("로그인 후 제품 등록이 가능합니다.");
        }

        Member findMember = memberRepository.findById(memberDto.getMember_id())
                .orElseThrow(()-> new MemberException("없는 회원 입니다."));

        Product product = Product.createProduct(productDto.getName(), productDto.getPrice(), productDto.getStock_quantity(), findMember);

        productRepository.save(product);


        return product.toDto();
    }

    /* 제품 목록 조회 */
    public List<ProductDto> listProudct() {

        List<Product> products = productRepository.findAll();

        List<ProductDto> productDtos = new ArrayList<>();

        for (Product product : products) {
            productDtos.add(new ProductDto(product));
        }

        return productDtos;
    }

    /* 제품 구매 */
    @Transactional
    public OrderDto purchaseProduct(Long productId, Long count, Long price, HttpSession session) {
        MemberDto memberDto = (MemberDto) session.getAttribute("memberDto");
        if(memberDto == null){
            throw new SessionNotFoundException("회원만 제품 구매가 가능합니다.");
        }

        Member findMember = memberRepository.findById(memberDto.getMember_id())
                .orElseThrow(() -> new MemberException("없는 회원 입니다."));

        Product findProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("없는 제품 입니다."));

        OrderProduct orderProduct = OrderProduct.createOrderProduct(findProduct, count, price);
        orderProductRepository.save(orderProduct);

        Order order = Order.createOrder(findMember, orderProduct);
        orderRepository.save(order);

        return new OrderDto(order.getId(), findProduct.getId(), order.getStatus().name(), order.getOrderDate(), count, price, orderProduct.getTotalPrice());
    }
}
