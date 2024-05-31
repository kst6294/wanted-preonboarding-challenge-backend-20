package com.wanted.challenge.product.service;

import com.wanted.challenge.account.entity.Account;
import com.wanted.challenge.account.repository.AccountRepository;
import com.wanted.challenge.exception.CustomException;
import com.wanted.challenge.exception.ExceptionStatus;
import com.wanted.challenge.product.entity.Product;
import com.wanted.challenge.product.entity.Purchase;
import com.wanted.challenge.product.model.PurchaseDetail;
import com.wanted.challenge.product.repository.ProductRepository;
import com.wanted.challenge.product.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final ProductRepository productRepository;
    private final PurchaseRepository purchaseRepository;
    private final AccountRepository accountRepository;

    public void approve(Long productId, Long buyerId, Long accountId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ExceptionStatus.NOT_FOUND));

        if (!product.getId().equals(accountId)) {
            throw new CustomException(ExceptionStatus.NOT_SELLER);
        }

        PurchaseDetail lastPurchaseDetail = purchaseRepository.retrieveLastPurchaseDetail(buyerId, productId);

        if (lastPurchaseDetail != PurchaseDetail.DEPOSIT) {
            throw new CustomException(ExceptionStatus.CAN_NOT_APPROVE);
        }

        Account buyer = accountRepository.getReferenceById(buyerId);

        purchaseRepository.save(new Purchase(buyer, product, PurchaseDetail.APPROVE));
    }
}
