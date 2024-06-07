package wanted.Market.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.Market.domain.product.dto.ProductAddRequest;
import wanted.Market.domain.product.entity.Product;
import wanted.Market.domain.product.entity.ProductStatus;
import wanted.Market.domain.product.repository.ProductRepository;
import wanted.Market.domain.transaction.entity.Transaction;
import wanted.Market.domain.transaction.repository.TransactionRepository;
import wanted.Market.global.exception.AppException;
import wanted.Market.global.exception.ErrorCode.ProductErrorCode;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final TransactionRepository transactionRepository;

    //제품 등록
    @Transactional
    public Product addProduct(ProductAddRequest productAddRequest, String username) {

        return productRepository.save(Product.builder()
                .name(productAddRequest.getName())
                .price(productAddRequest.getPrice())
                .status(ProductStatus.SALE)
                .sellerIdentifier(username)
            .build());
    }

    //모든 제품 조회
    public List<Product> getList(){
        return productRepository.findAll();
    }

    //제품 상세 조회
    public Product getProduct(long id) {
        return productRepository.findById(id).orElseThrow(() -> new AppException(ProductErrorCode.INVALID_PRODUCT_ID));
    }

    //거래 생성
    @Transactional
    public Transaction purchaseProduct(String username, long id) {
        Product product = getProduct(id);

        if (product.getSellerIdentifier().equals(username)) {
            throw new AppException(ProductErrorCode.SELLER_AND_BUYER_SAME);
        }

        if (product.getStatus() != ProductStatus.SALE) {
            throw new AppException(ProductErrorCode.PRODUCT_STATUS_NOT_SALE);
        }

        // 예약중으로 상태 변경
        product.setStatus(ProductStatus.RESERVED);
        productRepository.save(product); // 상태 변경 저장

        // 변경된 상태로 트랜잭션 저장
        Transaction transaction = Transaction.builder()
            .buyerName(username)
            .product(product)
            .status(false)
            .build();
        transactionRepository.save(transaction);

        return transaction;
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }
}
