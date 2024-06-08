package market.market.domain.product.facade;

import lombok.RequiredArgsConstructor;
import market.market.domain.product.domain.Product;
import market.market.domain.product.domain.repository.ProductRepository;
import market.market.global.error.ErrorCode;
import market.market.global.error.exeception.CustomException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductFacade {
    private final ProductRepository productRepository;

    public Product getProductById(Long id) {
        return productRepository.findProductById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    public List<Product> getProductAllById(Sort sort){
        return productRepository.findAll(sort);
    }
}
