package wanted.pre.onboading.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.pre.onboading.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
