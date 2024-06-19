package market.market.domain.transaction.service;

import lombok.RequiredArgsConstructor;
import market.market.domain.product.domain.Product;
import market.market.domain.product.domain.repository.ProductRepository;
import market.market.domain.product.enums.Status;
import market.market.domain.product.facade.ProductFacade;
import market.market.domain.transaction.domain.Transaction;
import market.market.domain.transaction.domain.repository.TransactionRepository;
import market.market.domain.transaction.enums.TransactionStatus;
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
public class CreateTransactionService {
    private final ProductRepository productRepository;
    private final TransactionRepository transactionRepository;
    private final UserFacade userFacade;
    private final ProductFacade productFacade;
    private final TransactionFacade transactionFacade;

    @Transactional(rollbackFor = Exception.class, noRollbackFor = CustomException.class)
    public void execute(Long productId) {
        User user = userFacade.getCurrentUser();
        Product product = productFacade.getProductById(productId);

        // 판매자가 구매를 누르지 못하게 추가
        if(!Objects.equals(user.getId(), product.getUser().getId())) {
            // 2단계 추가
            if(product.getQuantity() > 0 && !transactionFacade.existsTransactionByIdAndProduct(user.getId(), product)) {

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
                product.updateQuantity();
            }else {
                if(transactionFacade.boolTransactionByUndefinedOrApproval(TransactionStatus.UNDEFINED, TransactionStatus.APPROVAL)) {
                    product.updateStatus(Status.Reservation);
                    throw new CustomException(ErrorCode.PRODUCT_ZERO_QUANTITY);
                }
                product.updateStatus(Status.Completion);
                throw new CustomException(ErrorCode.PRODUCT_COMPLETION_QUANTITY);
            }
        }
    }
}
