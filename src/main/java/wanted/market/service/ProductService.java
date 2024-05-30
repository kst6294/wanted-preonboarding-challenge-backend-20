package wanted.market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.market.entity.Order;
import wanted.market.entity.Product;
import wanted.market.entity.User;
import wanted.market.repository.OrderRepository;
import wanted.market.repository.ProductRepository;
import wanted.market.util.CurrentUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CurrentUser currentUser;

    // 상품 목록 조회
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 상품 상세조회
    public Map<String, Object> getProductById(Long productId) throws Exception {
        Map<String, Object> response = new HashMap<>();
        Optional<Product> productOpt = productRepository.findById(productId);

        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            response.put("product", product);

            // 인증된 사용자라면 주문 목록도 포함
            if(currentUser.getUser() != null){
                List<Order> orders = orderRepository.findByProduct(product);
                response.put("orders", orders);
            }
        }
        return response;
    }

    // 상품 등록
    @Transactional
    public void registerProduct(String name, int price, int quantity) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new Exception("사용자 인증이 필요합니다.");
        }

        WantedUserDetails userDetails = (WantedUserDetails) authentication.getPrincipal();

        User seller = userDetails.getUser();

        Product product = new Product();

        product.setName(name);
        product.setPrice(price);
        product.setSeller(seller);
        product.setQuantity(quantity);

        productRepository.save(product);
    }

    // 판매목록 조회
    @Transactional(readOnly = true)
    public List<Product> getMyProducts() throws Exception {
        User user = currentUser.getUser();
        if (user == null) {
            throw new Exception("사용자 인증이 필요합니다.");
        }
        return productRepository.findBySeller(user);
    }
}
