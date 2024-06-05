package wanted.pre.onboading;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import wanted.pre.onboading.entity.Product;
import wanted.pre.onboading.entity.ProductStatus;
import wanted.pre.onboading.entity.User;
import wanted.pre.onboading.repository.ProductRepository;
import wanted.pre.onboading.repository.UserRepository;
import wanted.pre.onboading.service.ProductService;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testProductCreate() {
        User user = new User();
        user.setUsername("아이유");
        userRepository.save(user);

        Product product = new Product();
        product.setProductName("Product1");
        product.setPrice(100);
        product.setStatus(ProductStatus.판매중);
        product.setQuantity(10);
        product.setSeller(user);

        productService.saveProduct(product);

        Product savedProduct = productRepository.findById(product.getProductId()).orElse(null);
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getProductName()).isEqualTo("Product1");
        assertThat(savedProduct.getPrice()).isEqualTo(100);
        assertThat(savedProduct.getStatus()).isEqualTo(ProductStatus.판매중);
        assertThat(savedProduct.getQuantity()).isEqualTo(10);
        assertThat(savedProduct.getSeller().getUsername()).isEqualTo("아이유");
    }

    @Test
    public void testProductPurchase() {
        User seller = new User();
        seller.setUsername("쿠키");
        userRepository.save(seller);

        Product product = new Product();
        product.setProductName("Product 2");
        product.setPrice(2500);
        product.setStatus(ProductStatus.판매중);
        product.setQuantity(10);
        product.setSeller(seller);

        productService.saveProduct(product);

        User buyer = new User();
        buyer.setUsername("buyer");
        userRepository.save(buyer);

        productService.purchaseProduct(product.getProductId(), buyer);

        Product updateProduct = productRepository.findById(product.getProductId()).orElse(null);
        assertThat(updateProduct).isNotNull();
        assertThat(updateProduct.getQuantity()).isEqualTo(9);
        assertThat(updateProduct.getStatus()).isEqualTo(ProductStatus.완료);
    }

    @Test
    public void testProductApprove() {
        User seller = new User();
        seller.setUsername("쿠키");
        userRepository.save(seller);

        Product product = new Product();
        product.setProductName("Product 2");
        product.setPrice(2500);
        product.setStatus(ProductStatus.판매중);
        product.setQuantity(1);
        product.setSeller(seller);

        productService.saveProduct(product);

        User buyer = new User();
        buyer.setUsername("buyer");
        userRepository.save(buyer);

        productService.purchaseProduct(product.getProductId(), buyer);

        productService.approvePurchase(product.getProductId(), seller);

        Product updateProduct = productRepository.findById(product.getProductId()).orElse(null);
        assertThat(updateProduct).isNotNull();
        assertThat(updateProduct.getStatus()).isEqualTo(ProductStatus.완료);
    }
}
