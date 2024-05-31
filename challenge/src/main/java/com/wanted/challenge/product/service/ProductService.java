package com.wanted.challenge.product.service;

import com.wanted.challenge.account.entity.Account;
import com.wanted.challenge.account.repository.AccountRepository;
import com.wanted.challenge.exception.CustomException;
import com.wanted.challenge.exception.ExceptionStatus;
import com.wanted.challenge.product.entity.Product;
import com.wanted.challenge.product.entity.Purchase;
import com.wanted.challenge.product.model.Price;
import com.wanted.challenge.product.model.PurchaseDetail;
import com.wanted.challenge.product.model.Quantity;
import com.wanted.challenge.product.repository.ProductRepository;
import com.wanted.challenge.product.repository.PurchaseRepository;
import com.wanted.challenge.product.response.ProductDetailResponse;
import com.wanted.challenge.product.response.ProductPreviewResponse;
import com.wanted.challenge.product.response.PurchaseBuyerResponse;
import com.wanted.challenge.product.response.PurchaseDetailResponse;
import com.wanted.challenge.product.response.PurchaseInfo;
import com.wanted.challenge.product.response.PurchaseProductResponse;
import com.wanted.challenge.product.response.ReserveProductResponse;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
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

    public Long register(String name, Price price, Long sellerId) {
        Account seller = accountRepository.getReferenceById(sellerId);

        Product product = new Product(seller, name, price);
        productRepository.save(product);

        return product.getId();
    }

    public void purchase(Long productId, Long buyerId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ExceptionStatus.NOT_FOUND));

        validBuyer(buyerId, product);
        validPurchaseAlready(buyerId, product.getId());
        validQuantity(product.getQuantity());

        product.purchase();

        Account buyer = accountRepository.getReferenceById(buyerId);
        Purchase purchase = new Purchase(buyer, product, PurchaseDetail.DEPOSIT);
        purchaseRepository.save(purchase);
    }

    private void validBuyer(Long buyerId, Product product) {
        if (product.getSeller().getId().equals(buyerId)) {
            throw new CustomException(ExceptionStatus.SELF_BUY);
        }
    }

    private void validPurchaseAlready(Long buyerId, Long productId) {
        if (purchaseRepository.isPurchaseAlready(buyerId, productId)) {
            throw new CustomException(ExceptionStatus.PURCHASE_ALREADY);
        }
    }

    private void validQuantity(Quantity quantity) {
        if (Objects.isNull(quantity) || quantity.value() <= 0) {
            throw new CustomException(ExceptionStatus.CAN_NOT_PURCHASE);
        }
    }

    public Page<ProductPreviewResponse> preview(Pageable pageable) {
        return productRepository.retrieveProductsPreview(pageable);
    }

    public ProductDetailResponse detail(Long productId, Optional<Long> optAccountId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ExceptionStatus.NOT_FOUND));

        if (optAccountId.isEmpty()) {
            return new ProductDetailResponse(product, Collections.emptyList());
        }

        Long accountId = optAccountId.get();
        List<PurchaseInfo> purchaseInfos = createPurchaseInfos(product, accountId);

        return new ProductDetailResponse(product, purchaseInfos);
    }

    private List<PurchaseInfo> createPurchaseInfos(Product product, Long accountId) {
        if (product.getSeller().getId().equals(accountId)) {
            return createBuyerInfos(product.getId());
        }

        return createPurchaseDetailInfos(accountId);
    }

    private List<PurchaseInfo> createBuyerInfos(Long productId) {
        List<Purchase> purchases = purchaseRepository.findByProductId(productId);

        Map<Long, List<PurchaseDetail>> buyerPurchaseDetails = purchases.stream()
                .sorted(Comparator.comparing(Purchase::getId).reversed())
                .collect(Collectors.groupingBy(purchase -> purchase.getBuyer().getId(), LinkedHashMap::new,
                        Collectors.mapping(Purchase::getPurchaseDetail, Collectors.toList())));

        return buyerPurchaseDetails.entrySet()
                .stream()
                .map(entry -> new PurchaseBuyerResponse(entry.getKey(), entry.getValue()))
                .map(PurchaseInfo.class::cast)
                .toList();
    }

    private List<PurchaseInfo> createPurchaseDetailInfos(Long accountId) {
        List<Purchase> purchases = purchaseRepository.findByBuyerId(accountId);

        return purchases.stream()
                .map(Purchase::getPurchaseDetail)
                .map(PurchaseDetailResponse::new)
                .map(PurchaseInfo.class::cast)
                .toList();
    }

    public Page<PurchaseProductResponse> purchaseProducts(Long buyerId, Pageable pageable) {
        return productRepository.retrievePurchaseProducts(buyerId, pageable);
    }

    public Page<ReserveProductResponse> reserveProducts(Long buyerId, Pageable pageable) {
        return productRepository.retrieveReserveProducts(buyerId, pageable);
    }
}
