package wanted.preonboard.market.mapper;

import org.apache.ibatis.annotations.Mapper;
import wanted.preonboard.market.domain.product.Product;

import java.util.List;

@Mapper
public interface ProductMapper {
    int insertProduct(Product product);
    List<Product> getProducts();
    Product getProductById(Integer id);
    int updateProductById(Product product);
}
