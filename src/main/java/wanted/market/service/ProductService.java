package wanted.market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.market.entity.Product;
import wanted.market.entity.User;
import wanted.market.repository.ProductRepository;
import wanted.market.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // 상품 목록 조회
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 상품 상세조회
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // 상품 등록
    @Transactional
    public void registerProduct(String name, int price) throws Exception {
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

        productRepository.save(product);
    }
}
