package wanted.preonboard.market.service.Product;

import wanted.preonboard.market.domain.product.dto.ProductInsertDto;
import wanted.preonboard.market.domain.product.dto.ProductResDto;
import wanted.preonboard.market.domain.product.dto.ProductUpdateDto;

import java.util.List;

public interface ProductService {
    Boolean createProduct(Integer sellerId, ProductInsertDto product);

    List<ProductResDto> getProducts();

    ProductResDto getProductById(Integer productId);

    Boolean updateProductById(Integer productId, ProductUpdateDto product);
}
