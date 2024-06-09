package wanted.market.domain.transcation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.market.domain.member.repository.MemberRepository;
import wanted.market.domain.member.repository.entity.Member;
import wanted.market.domain.product.repository.ProductRepository;
import wanted.market.domain.product.repository.entity.Product;
import wanted.market.domain.transcation.repository.TransactionRepository;
import wanted.market.domain.transcation.repository.entity.Transaction;
import wanted.market.domain.transcation.service.dto.request.TransactionCancelServiceRequest;
import wanted.market.domain.transcation.service.dto.request.TransactionCreateServiceRequest;
import wanted.market.domain.transcation.service.dto.response.TransactionCreateResponse;
import wanted.market.domain.transcation.service.dto.response.TransactionListResponse;
import wanted.market.global.exception.RestApiException;

import java.util.List;
import java.util.stream.Collectors;

import static wanted.market.global.exception.CommonErrorCode.*;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public TransactionCreateResponse createTransaction(TransactionCreateServiceRequest request) {
        Member member = memberRepository.findMemberByEmail(request.getEmail())
                        .orElseThrow(() -> new RestApiException(USER_NOT_FOUND));
        Product product = productRepository.findById(request.getProductId())
                        .orElseThrow(() -> new RestApiException(PRODUCT_NOT_FOUND));

        Transaction transaction = transactionRepository.save(request.toTransaction(member, product));
        product.updateReservationStatus();

        return TransactionCreateResponse.of(transaction);
    }

    @Override
    public List<TransactionListResponse> findTransactionWithMember(String email) {
        Member member = memberRepository.findMemberByEmail(email)
                .orElseThrow(() -> new RestApiException(USER_NOT_FOUND));

        List<Transaction> transactions = transactionRepository.findAllByMember(member);

        return transactions.stream()
                .map(TransactionListResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean cancelTransaction(TransactionCancelServiceRequest request) {
        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(() -> new RestApiException(TRANSACTION_NOT_FOUND));

        try {
            transaction.getProduct().bringBackQuantity();
            transaction.cancelTransaction();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean completeTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RestApiException(TRANSACTION_NOT_FOUND));

        try {
            transaction.completeTransaction();
            transaction.getProduct().completeTransaction();
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
