package wanted.market.domain.transcation.service;

import wanted.market.domain.transcation.service.dto.request.TransactionCancelServiceRequest;
import wanted.market.domain.transcation.service.dto.request.TransactionCreateServiceRequest;
import wanted.market.domain.transcation.service.dto.response.TransactionCreateResponse;
import wanted.market.domain.transcation.service.dto.response.TransactionListResponse;

import java.util.List;

public interface TransactionService {
    TransactionCreateResponse createTransaction(TransactionCreateServiceRequest request);

    List<TransactionListResponse> findTransactionWithMember(String email);

    boolean cancelTransaction(TransactionCancelServiceRequest request);

    boolean completeTransaction(Long transactionId);
}
