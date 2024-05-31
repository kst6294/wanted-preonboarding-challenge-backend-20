package wanted.preonboard.market.service.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wanted.preonboard.market.domain.product.Product;
import wanted.preonboard.market.domain.product.dto.ProductInsertDto;
import wanted.preonboard.market.domain.product.dto.ProductResDto;
import wanted.preonboard.market.domain.product.dto.ProductUpdateDto;
import wanted.preonboard.market.mapper.ProductMapper;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public Boolean createProduct(Integer sellerId, ProductInsertDto product) {
        Product newProduct = new Product();
        newProduct.setSellerId(sellerId);
        newProduct.setName(product.getName());
        newProduct.setPrice(product.getPrice());
        return productMapper.insertProduct(newProduct) == 1;
    }

    @Override
    public List<ProductResDto> getProducts() {
        return productMapper.getProducts();
    }

    @Override
    public ProductResDto getProductById(Integer id) {
        return productMapper.getProductById(id);
    }

    @Override
    public Boolean updateProductById(Integer productId, ProductUpdateDto product) {
        Product productToUpdate = productMapper.getProductById(productId);
        if (product.getName() != null) {
            productToUpdate.setName(product.getName().get());
        }
        if (product.getPrice() != null) {
            productToUpdate.setPrice(product.getPrice().get());
        }
        if (product.getState() != null) {
            productToUpdate.setState(product.getState().get());
        }
        return productMapper.updateProductById(productToUpdate) == 1;
    }
}
