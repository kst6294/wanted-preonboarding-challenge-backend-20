package market.market.domain.transaction.service;

import lombok.RequiredArgsConstructor;
import market.market.domain.product.domain.Product;
import market.market.domain.product.enums.Status;
import market.market.domain.product.facade.ProductFacade;
import market.market.domain.transaction.domain.Transaction;
import market.market.domain.transaction.facade.TransactionFacade;
import market.market.domain.user.domain.User;
import market.market.domain.user.facade.UserFacade;
import market.market.global.error.ErrorCode;
import market.market.global.error.exeception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UpdateTransactionService {
    private final UserFacade userFacade;
    private final TransactionFacade transactionFacade;

    @Transactional
    public void execute(Long transactionId) {
        User user = userFacade.getCurrentUser();
        Transaction transaction = transactionFacade.getTransactionById(transactionId);

        if (Objects.equals(user.getId(), transaction.getSeller_id())) {
            Product product = transaction.getProduct();
            if(product.getStatus() == Status.Completion) {
                throw new CustomException(ErrorCode.TRANSACTION_ALREADY_EXISTS);
            }
            product.updateStatus(Status.Completion);
        }
    }
}
