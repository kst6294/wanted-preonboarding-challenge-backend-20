package wanted.Market.domain.transaction.serivce;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.Market.domain.product.entity.Product;
import wanted.Market.domain.product.entity.ProductStatus;
import wanted.Market.domain.product.service.ProductService;
import wanted.Market.domain.transaction.dto.TransactionDto;
import wanted.Market.domain.transaction.entity.Transaction;
import wanted.Market.domain.transaction.repository.TransactionRepository;
import wanted.Market.global.exception.AppException;
import wanted.Market.global.exception.ErrorCode.ProductErrorCode;
import wanted.Market.global.exception.ErrorCode.TransactionErrorCode;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final ProductService productService;

    //내가 구매한 정보 조회
    public List<TransactionDto> getMyTransaction(String username){
        List<Transaction> transactions = transactionRepository.findByBuyerNameAndStatus(username, true)
            .orElseThrow(() -> new AppException(TransactionErrorCode.INVALID_TRANSACTION));
        return transactions.stream()
            .map(TransactionDto::fromEntity)
            .collect(Collectors.toList());
    }

    //구매자 기준으로 예약 중인 거래 내역 조회
    public List<TransactionDto> getBuyerTransaction(String username){
        List<Transaction> transactions = transactionRepository.findByBuyerNameAndStatus(username, false)
            .orElseThrow(() -> new AppException(TransactionErrorCode.INVALID_TRANSACTION));
        return transactions.stream()
            .map(TransactionDto::fromEntity)
            .collect(Collectors.toList());
    }

    //판매자 기준으로 예약 중인 거래 내역 조회
    public List<TransactionDto> getSellerTransaction(String username){
        List<Transaction> transactions = transactionRepository.findByProductSellerIdentifierAndStatus(username, false)
            .orElseThrow(() -> new AppException(TransactionErrorCode.INVALID_TRANSACTION));
        return transactions.stream()
            .map(TransactionDto::fromEntity)
            .collect(Collectors.toList());
    }

    //제품에 대한 거래 내역 조회
    public List<TransactionDto> getTransactionsByProductId(long productId){
        List<Transaction> transactions = transactionRepository.findByProductId(productId)
            .orElseThrow(() -> new AppException(TransactionErrorCode.INVALID_TRANSACTION));
        return transactions.stream()
            .map(TransactionDto::fromEntity)
            .collect(Collectors.toList());
    }
    @Transactional
    public Transaction approveTransaction(String username, long transactionId){
        Transaction transaction = transactionRepository.findById(transactionId)
            .orElseThrow(() -> new AppException(TransactionErrorCode.INVALID_TRANSACTION));

        // 판매자와 거래의 판매자가 같은지 확인
        if (!transaction.getProduct().getSellerIdentifier().equals(username)) {
            throw new AppException(ProductErrorCode.SELLER_AND_BUYER_SAME);
        }

        // 거래의 상태를 완료로 변경
        transaction.setStatus(true);
        transactionRepository.save(transaction);

        // 제품 상태를 완료로 변경
        Product product = transaction.getProduct();
        product.setStatus(ProductStatus.COMPLETED);
        productService.saveProduct(product);

        return transaction;
    }
}
