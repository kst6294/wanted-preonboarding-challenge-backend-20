package wanted.preonboard.market.mapper;

import org.apache.ibatis.annotations.Mapper;
import wanted.preonboard.market.domain.product.Product;
import wanted.preonboard.market.domain.product.dto.ProductResDto;

import java.util.List;

@Mapper
public interface ProductMapper {
    int insertProduct(Product product);

    List<ProductResDto> getProducts();

    ProductResDto getProductById(Integer id);
    int updateProductById(Product product);
}
