package com.wanted.challenge.transact.service;

import com.wanted.challenge.account.entity.Account;
import com.wanted.challenge.account.repository.AccountRepository;
import com.wanted.challenge.exception.CustomException;
import com.wanted.challenge.exception.ExceptionStatus;
import com.wanted.challenge.product.entity.Product;
import com.wanted.challenge.product.repository.ProductRepository;
import com.wanted.challenge.transact.entity.Transact;
import com.wanted.challenge.transact.model.TransactDetail;
import com.wanted.challenge.transact.repository.TransactRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactService {

    private final ProductRepository productRepository;
    private final TransactRepository transactRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public void approve(Long productId, Long buyerId, Long sellerId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ExceptionStatus.NOT_FOUND));

        if (!product.getId().equals(sellerId)) {
            throw new CustomException(ExceptionStatus.NOT_SELLER);
        }

        TransactDetail lastTransactDetail = transactRepository.retrieveLastTransactDetail(buyerId, productId);

        if (lastTransactDetail != TransactDetail.DEPOSIT) {
            throw new CustomException(ExceptionStatus.CAN_NOT_APPROVE);
        }

        Account buyer = accountRepository.getReferenceById(buyerId);

        transactRepository.save(new Transact(buyer, product, TransactDetail.APPROVE));
    }

    @Transactional
    public void confirm(Long productId, Long buyerId) {
        Product product = productRepository.findProductWithUpdateLockById(productId)
                .orElseThrow(() -> new CustomException(ExceptionStatus.NOT_FOUND));

        TransactDetail lastTransactDetail = transactRepository.retrieveLastTransactDetail(buyerId, productId);

        if (lastTransactDetail != TransactDetail.APPROVE) {
            throw new CustomException(ExceptionStatus.CAN_NOT_CONFIRM);
        }

        Account buyer = accountRepository.getReferenceById(buyerId);
        transactRepository.save(new Transact(buyer, product, TransactDetail.CONFIRM));

        isAllConfirm(product);
    }

    private void isAllConfirm(Product product) {
        Set<TransactDetail> transactDetails = transactRepository.retrieveProductTransactDetails(product);

        if (transactDetails.size() == 1 && transactDetails.contains(TransactDetail.CONFIRM)) {
            product.complete();
        }
    }
}
