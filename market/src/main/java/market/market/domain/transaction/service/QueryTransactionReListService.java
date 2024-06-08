package market.market.domain.transaction.service;

import lombok.RequiredArgsConstructor;
import market.market.domain.product.enums.Status;
import market.market.domain.transaction.domain.Transaction;
import market.market.domain.transaction.facade.TransactionFacade;
import market.market.domain.transaction.presentation.dto.response.QueryTransactionListResponse;
import market.market.domain.user.domain.User;
import market.market.domain.user.facade.UserFacade;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryTransactionReListService {
    private final UserFacade userFacade;
    private final TransactionFacade transactionFacade;

    @Transactional(readOnly = true)
    public List<QueryTransactionListResponse> execute() {
        User user = userFacade.getCurrentUser();

        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<Transaction> transactions = transactionFacade.getTransactionAllById(sort);

        return transactions.stream()
                .filter(transaction -> transaction.getProduct().getUser() == user)
                .filter(transaction -> transaction.getProduct().getStatus() == Status.Reservation)
                .map(transaction -> QueryTransactionListResponse.builder()
                        .transactionId(transaction.getId())
                        .productId(transaction.getProduct().getId())
                        .productName(transaction.getProduct().getName())
                        .price(transaction.getPrice())
                        .build()
                )
                .toList();
    }
}
