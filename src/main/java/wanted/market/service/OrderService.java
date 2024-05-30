package wanted.market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.market.entity.Order;
import wanted.market.entity.Product;
import wanted.market.entity.ProductStatus;
import wanted.market.entity.User;
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

    @Transactional
    public Order registerOrder(Long productId) throws Exception{

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품이 품절상태거나 접근 오류입니다."));

        // 상품의 판매상태 확인
        if (product.getProductStatus() == ProductStatus.RESERVED) {
            throw new RuntimeException("이미 예약중인 상품입니다.");
        }
        if (product.getProductStatus() == ProductStatus.SOLD_OUT) {
            throw new RuntimeException("이미 품절된 상품입니다.");
        }

        User buyer = currentUser.getUser();

        // 예약상태로 변경
        product.reserve();

        Order order = new Order();
        order.setBuyer(buyer);
        order.setProduct(product);
        return orderRepository.save(order);
    }

    @Transactional
    public Order confirmOrder(Long orderId) throws Exception{

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

        // 상품의 판매상태 확인
        if (product.getProductStatus() == ProductStatus.SOLD_OUT) {
            throw new RuntimeException("이미 품절된 상품입니다.");
        }
        if (product.getProductStatus() == ProductStatus.AVAILABLE) {
            throw new RuntimeException("예약중인 상품이 아닙니다.");
        }

        // 품절상태로 변경
        product.sell();

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
}
