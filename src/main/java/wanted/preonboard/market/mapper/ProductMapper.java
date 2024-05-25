package wanted.preonboard.market.mapper;

import org.apache.ibatis.annotations.Mapper;
import wanted.preonboard.market.domain.dto.ProductInsertDto;
import wanted.preonboard.market.domain.entity.Product;

import java.util.List;

@Mapper
public interface ProductMapper {
    int insertProduct(Product product);
    List<Product> getProducts();
    Product getProductById(Long id);
    int updateProductById(Product product);
}
