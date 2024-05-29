package wanted.preonboard.market.service.Product;

import org.springframework.security.core.Authentication;
import wanted.preonboard.market.domain.dto.ProductInsertDto;
import wanted.preonboard.market.domain.dto.ProductUpdateDto;
import wanted.preonboard.market.domain.entity.Product;

import java.util.List;

public interface ProductService {
    Boolean createProduct(Integer sellerId, ProductInsertDto product);

    List<Product> getProducts();

    Product getProductById(Integer productId);

    Boolean updateProductById(Integer productId, ProductUpdateDto product);
}
