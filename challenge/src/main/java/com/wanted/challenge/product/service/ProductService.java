package com.wanted.challenge.product.service;

import com.wanted.challenge.account.entity.Account;
import com.wanted.challenge.account.model.AccountDetail;
import com.wanted.challenge.account.repository.AccountRepository;
import com.wanted.challenge.exception.CustomException;
import com.wanted.challenge.exception.ExceptionStatus;
import com.wanted.challenge.product.entity.Product;
import com.wanted.challenge.product.entity.Purchase;
import com.wanted.challenge.product.model.Price;
import com.wanted.challenge.product.model.PurchaseDetail;
import com.wanted.challenge.product.repository.ProductRepository;
import com.wanted.challenge.product.repository.PurchaseRepository;
import com.wanted.challenge.product.response.ProductPreviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;
    private final PurchaseRepository purchaseRepository;

    public Long register(String name, Price price, AccountDetail accountDetail) {
        Long sellerId = accountDetail.getAccountId();
        Account seller = accountRepository.getReferenceById(sellerId);

        Product product = new Product(seller, name, price);
        productRepository.save(product);

        return product.getId();
    }

    public void purchase(Long productId, AccountDetail accountDetail) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ExceptionStatus.NOT_FOUND));

        Long buyerId = accountDetail.getAccountId();
        validBuyer(buyerId, product);

        Account buyer = accountRepository.getReferenceById(buyerId);
        Purchase purchase = new Purchase(buyer, PurchaseDetail.DEPOSIT);
        purchaseRepository.save(purchase);
    }

    private void validBuyer(Long buyerId, Product product) {
        if (product.getSeller().getId().equals(buyerId)) {
            throw new CustomException(ExceptionStatus.SELF_BUY);
        }
    }

    public Page<ProductPreviewResponse> preview(Pageable pageable) {
        return productRepository.retrieveProductsPreview(pageable);
    }
}
