package com.wanted.challenge.transact.service;

import com.wanted.challenge.exception.CustomException;
import com.wanted.challenge.exception.ExceptionStatus;
import com.wanted.challenge.product.entity.Product;
import com.wanted.challenge.product.repository.ProductRepository;
import com.wanted.challenge.transact.entity.Transact;
import com.wanted.challenge.transact.entity.TransactLog;
import com.wanted.challenge.transact.model.TransactState;
import com.wanted.challenge.transact.repository.TransactLogRepository;
import com.wanted.challenge.transact.repository.TransactRepository;
import java.util.List;
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
    private final TransactLogRepository transactLogRepository;

    @Transactional
    public void approve(Long productId, Long buyerId, Long sellerId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ExceptionStatus.NOT_FOUND));

        if (!product.getSeller().getId().equals(sellerId)) {
            throw new CustomException(ExceptionStatus.NOT_SELLER);
        }

        Transact transact = transactRepository.findByBuyerIdAndProductId(buyerId, productId)
                .orElseThrow(() -> new CustomException(ExceptionStatus.NOT_FOUND));

        List<TransactState> transactStates = transactRepository.retrieveAllTransactState(transact.getId());

        if (transactStates.contains(TransactState.APPROVE)) {
            throw new CustomException(ExceptionStatus.APPROVE_ALREADY);
        }

        transactLogRepository.save(new TransactLog(transact, TransactState.APPROVE));
    }

    @Transactional
    public void confirm(Long productId, Long buyerId) {
        Product product = productRepository.findProductWithUpdateLockById(productId)
                .orElseThrow(() -> new CustomException(ExceptionStatus.NOT_FOUND));

        TransactState lastTransactState = transactRepository.retrieveLastTransactState(buyerId, productId);

        if (lastTransactState != TransactState.APPROVE) {
            throw new CustomException(ExceptionStatus.CAN_NOT_CONFIRM);
        }

        Transact transact = transactRepository.findByBuyerIdAndProductId(buyerId, productId)
                .orElseThrow(() -> new CustomException(ExceptionStatus.NOT_FOUND));

        transactLogRepository.save(new TransactLog(transact, TransactState.CONFIRM));

        isAllConfirm(product);
    }

    private void isAllConfirm(Product product) {
        Set<TransactState> transactStates = transactRepository.retrieveDistinctProductTransactStates(product);

        if (transactStates.size() == 1 && transactStates.contains(TransactState.CONFIRM)) {
            product.complete();
        }
    }
}
