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
import com.wanted.challenge.product.response.ProductDetailResponse;
import com.wanted.challenge.product.response.ProductPreviewResponse;
import com.wanted.challenge.product.response.PurchaseProductResponse;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
        Purchase purchase = new Purchase(buyer, product, PurchaseDetail.DEPOSIT);
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

    public ProductDetailResponse detail(Long productId, AccountDetail accountDetail) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ExceptionStatus.NOT_FOUND));

        if (Objects.isNull(accountDetail)) {
            return new ProductDetailResponse(product, Collections.emptyList());
        }

        Long buyerId = accountDetail.getAccountId();
        List<Purchase> purchases = purchaseRepository.findByBuyerId(buyerId);

        List<PurchaseDetail> purchaseDetails = purchases.stream()
                .map(Purchase::getPurchaseDetail)
                .toList();

        return new ProductDetailResponse(product, purchaseDetails);
    }

    public Page<PurchaseProductResponse> purchaseProducts(AccountDetail accountDetail, Pageable pageable) {
        Long buyerId = accountDetail.getAccountId();
        return productRepository.retrievePurchaseProducts(buyerId, pageable);
    }
}
