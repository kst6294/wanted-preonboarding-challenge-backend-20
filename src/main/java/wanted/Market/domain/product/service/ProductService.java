package wanted.Market.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.Market.domain.product.dto.ProductAddRequest;
import wanted.Market.domain.product.entity.Product;
import wanted.Market.domain.product.entity.ProductStatus;
import wanted.Market.domain.product.repository.ProductRepository;
import wanted.Market.global.exception.AppException;
import wanted.Market.global.exception.ErrorCode.ProductErrorCode;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    @Transactional
    public Product addProduct(ProductAddRequest productAddRequest) {
        return productRepository.save(Product.builder()
                .name(productAddRequest.getName())
                .price(productAddRequest.getPrice())
                .status(ProductStatus.valueOf(productAddRequest.getStatus()))
            .build());
    }
    public List<Product> getList(){
        return productRepository.findAll();
    }
    public Product getProduct(long id){
        return productRepository.findById(id).orElseThrow(()-> new AppException(ProductErrorCode.INVALID_PRODUCT_ID));
    }

}
