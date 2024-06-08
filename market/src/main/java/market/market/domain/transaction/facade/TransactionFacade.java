package market.market.domain.transaction.facade;

import lombok.RequiredArgsConstructor;
import market.market.domain.product.domain.Product;
import market.market.domain.transaction.domain.Transaction;
import market.market.domain.transaction.domain.repository.TransactionRepository;
import market.market.global.error.ErrorCode;
import market.market.global.error.exeception.CustomException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TransactionFacade {
    private final TransactionRepository transactionRepository;

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findTransactionById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.TRANSACTION_NOT_FOUND));
    }

    public Transaction getTransactionByProduct(Product product) {
        return transactionRepository.findTransactionByProduct(product)
                .orElseThrow(() -> new CustomException(ErrorCode.TRANSACTION_NOT_FOUND));
    }

    public List<Transaction> getTransactionAllById(Sort sort){
        return transactionRepository.findAll(sort);
    }
}
