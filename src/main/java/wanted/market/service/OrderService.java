package wanted.market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.market.entity.*;
import wanted.market.repository.OrderRepository;
import wanted.market.repository.ProductRepository;
import wanted.market.util.CurrentUser;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CurrentUser currentUser;

    // 구매요청
    @Transactional
    public Order registerOrder(Long productId) throws Exception{

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품이 품절상태거나 접근 오류입니다."));

        // 상품의 판매상태 확인
        if (product.getProductStatus() == ProductStatus.RESERVED) {
            throw new RuntimeException("모든 수량이 판매예약중인 상품입니다.");
        }
        if (product.getProductStatus() == ProductStatus.SOLD_OUT) {
            throw new RuntimeException("이미 품절된 상품입니다.");
        }

        User buyer = currentUser.getUser();

        // 상품수량 체크 후 상태변경
        int quantity = product.getQuantity();

        // 판매총수량 == 구매요청중인 수량 + 판매승인한 수량일 경우 추가판매 불가능으로 상태변경
        long totalOrderCount = orderRepository.countByProductAndOrderStatus(product, OrderStatus.APPROVED)
                + orderRepository.countByProductAndOrderStatus(product, OrderStatus.REQUEST) + 1;

        if(quantity == (int) totalOrderCount){
            // 판매예약완료로 상태 변경
            product.reserve();
            productRepository.save(product);
        }

        Order order = new Order();
        order.setBuyer(buyer);
        order.setProduct(product);
        // 현재 구매 가격 세팅
        order.setBuyPrice(product.getPrice());

        return orderRepository.save(order);
    }

    // 판매승인
    @Transactional
    public Order approveOrder(Long orderId) throws Exception{

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("주문 정보 오류입니다."));;

        Product product = productRepository.findById(order.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("상품 정보 오류입니다."));

        // 판매승인 요청한 유저
        User user = currentUser.getUser();

        // 판매자 정보 확인
        if(!product.getSeller().getUsername().equals(user.getUsername())) {
            throw new RuntimeException("판매자 정보 오류");
        }

        // 판매승인 상태로 변경
        order.approve();

        return orderRepository.save(order);
    }

    // 구매목록 조회
    public List<Order> getMyOrders() throws Exception {
        User user = currentUser.getUser();
        if (user == null) {
            throw new Exception("사용자 인증이 필요합니다.");
        }
        return orderRepository.findByBuyer(user);
    }

    // 구매확정
    public Order purchaseConfirmOrder(Long orderId) throws Exception {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("주문 정보 오류입니다."));;

        Product product = productRepository.findById(order.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("상품 정보 오류입니다."));

        // 구매확정 요청한 유저
        User user = currentUser.getUser();

        // 구매자 정보 확인
        if(!order.getBuyer().getUsername().equals(user.getUsername())) {
            throw new RuntimeException("구매자 정보 오류");
        }

        // 주문상태 확인
        if (order.getOrderStatus() == OrderStatus.REQUEST){
            throw new RuntimeException("판매승인이 필요한 주문입니다.");
        }

        // 구매확정
        order.confirm();

        // 상품판매
        product.sell();

        productRepository.save(product);
        return orderRepository.save(order);
    }
}
