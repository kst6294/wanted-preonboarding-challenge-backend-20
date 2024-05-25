package wanted.preonboard.market.service.Product;

import org.springframework.security.core.Authentication;
import wanted.preonboard.market.domain.dto.ProductInsertDto;
import wanted.preonboard.market.domain.dto.ProductUpdateDto;
import wanted.preonboard.market.domain.entity.Product;

import java.util.List;

public interface ProductService {
    Boolean createProduct(Long sellerId, ProductInsertDto product);

    List<Product> getProducts();

    Product getProductById(Long productId);

    Boolean updateProductById(Long productId, ProductUpdateDto product);
}
