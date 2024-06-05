package wanted.pre.onboading.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.pre.onboading.entity.Product;
import wanted.pre.onboading.entity.ProductStatus;
import wanted.pre.onboading.entity.User;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByPurchasesBuyer(User buyer);
    List<Product> findByUser(User seller, User buyer, ProductStatus status);
}
