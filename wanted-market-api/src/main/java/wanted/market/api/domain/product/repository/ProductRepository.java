package wanted.market.api.domain.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wanted.market.api.domain.product.entity.Product;
import wanted.market.api.domain.product.enums.ProductStatus;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByProductStatusNot(ProductStatus productStatus, Pageable pageable);

    Page<Product> findAllByProductStatus(ProductStatus productStatus, Pageable pageable);
}
