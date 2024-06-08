package market.market.domain.transaction.service;

import lombok.RequiredArgsConstructor;
import market.market.domain.product.domain.Product;
import market.market.domain.product.enums.Status;
import market.market.domain.product.facade.ProductFacade;
import market.market.domain.transaction.domain.Transaction;
import market.market.domain.transaction.domain.repository.TransactionRepository;
import market.market.domain.user.domain.User;
import market.market.domain.user.facade.UserFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateTransactionService {
    private final TransactionRepository transactionRepository;
    private final UserFacade userFacade;
    private final ProductFacade productFacade;

    @Transactional
    public void execute(Long productId) {
        User user = userFacade.getCurrentUser();
        Product product = productFacade.getProductById(productId);
        product.updateStatus(Status.Reservation);
        transactionRepository.save(
                Transaction.builder()
                        .buyer_id(user.getId())
                        .buyer_accountid(user.getAccountId())
                        .seller_id(product.getUser().getId())
                        .seller_accountid(product.getUser().getAccountId())
                        .product(product)
                        .price(product.getPrice())
                        .build()
        );
    }
}
