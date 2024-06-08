package market.market.domain.transaction.service;

import lombok.RequiredArgsConstructor;
import market.market.domain.product.domain.Product;
import market.market.domain.product.enums.Status;
import market.market.domain.product.facade.ProductFacade;
import market.market.domain.transaction.domain.Transaction;
import market.market.domain.transaction.facade.TransactionFacade;
import market.market.domain.transaction.presentation.dto.response.QueryTransactionDetailResponse;
import market.market.domain.user.domain.User;
import market.market.domain.user.facade.UserFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryProductTransactionDetailService {
    private final UserFacade userFacade;
    private final ProductFacade productFacade;
    private final TransactionFacade transactionFacade;

    @Transactional
    public QueryTransactionDetailResponse execute(Long productId) {
        Product product = productFacade.getProductById(productId);
        User user = userFacade.getCurrentUser();

        if(product.getStatus() == Status.Reservation || product.getStatus() == Status.Completion) {
            Transaction transaction = transactionFacade.getTransactionByProduct(product);
            if(product.getUser() == user) {
                return QueryTransactionDetailResponse.builder()
                        .transactionId(transaction.getId())
                        .buyerId(transaction.getBuyer_id())
                        .buyerName(transaction.getBuyer_accountid())
                        .sellerId(transaction.getSeller_id())
                        .sellerName(transaction.getSeller_accountid())
                        .status(product.getStatus().getTitle())
                        .price(product.getPrice()).build();
            }
            else return null;
        }
        return null;
    }
}
