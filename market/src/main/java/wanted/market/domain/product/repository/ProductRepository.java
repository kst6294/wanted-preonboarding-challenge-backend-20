package wanted.market.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wanted.market.domain.product.repository.entity.Product;
import wanted.market.domain.product.service.dto.response.ProductListResponse;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
