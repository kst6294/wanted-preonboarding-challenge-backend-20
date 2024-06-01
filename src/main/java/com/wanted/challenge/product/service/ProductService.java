package com.wanted.challenge.product.service;

import com.wanted.challenge.account.entity.Account;
import com.wanted.challenge.account.repository.AccountRepository;
import com.wanted.challenge.exception.CustomException;
import com.wanted.challenge.exception.ExceptionStatus;
import com.wanted.challenge.product.entity.Product;
import com.wanted.challenge.product.model.Price;
import com.wanted.challenge.product.model.Quantity;
import com.wanted.challenge.product.repository.ProductRepository;
import com.wanted.challenge.product.response.ProductDetailResponse;
import com.wanted.challenge.product.response.ProductPreviewResponse;
import com.wanted.challenge.product.response.PurchaseBuyerResponse;
import com.wanted.challenge.product.response.PurchaseDetailResponse;
import com.wanted.challenge.product.response.PurchaseInfo;
import com.wanted.challenge.product.response.PurchaseProductResponse;
import com.wanted.challenge.product.response.ReserveProductResponse;
import com.wanted.challenge.transact.entity.Transact;
import com.wanted.challenge.transact.entity.TransactLog;
import com.wanted.challenge.transact.model.TransactState;
import com.wanted.challenge.transact.repository.TransactLogRepository;
import com.wanted.challenge.transact.repository.TransactRepository;
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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;
    private final TransactRepository transactRepository;
    private final TransactLogRepository transactLogRepository;

    @Transactional
    public Long register(String name, Price price, Quantity quantity, Long sellerId) {
        Account seller = accountRepository.getReferenceById(sellerId);

        Product product = new Product(seller, name, price, quantity);
        productRepository.save(product);

        return product.getId();
    }

    @Transactional
    public void purchase(Long productId, Long buyerId) {
        Product product = productRepository.findProductWithUpdateLockById(productId)
                .orElseThrow(() -> new CustomException(ExceptionStatus.NOT_FOUND));

        validBuyer(buyerId, product);
        validPurchaseAlready(buyerId, product.getId());
        validQuantity(product.getQuantity());

        product.purchase();

        Account buyer = accountRepository.getReferenceById(buyerId);
        Transact transact = new Transact(buyer, product);
        transactRepository.save(transact);
    }

    private void validBuyer(Long buyerId, Product product) {
        if (product.getSeller().getId().equals(buyerId)) {
            throw new CustomException(ExceptionStatus.SELF_BUY);
        }
    }

    private void validPurchaseAlready(Long buyerId, Long productId) {
        Optional<Transact> optTransact = transactRepository.findByBuyerIdAndProductId(buyerId, productId);

        if (optTransact.isPresent()) {
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
        List<PurchaseInfo> purchaseInfos = getPurchaseInfos(product, accountId);

        return new ProductDetailResponse(product, purchaseInfos);
    }

    private List<PurchaseInfo> getPurchaseInfos(Product product, Long accountId) {
        Long productId = product.getId();

        if (product.getSeller().getId().equals(accountId)) {
            return getAllBuyerInfo(productId);
        }

        return getAllTransactStateInfo(productId, accountId);
    }

    private List<PurchaseInfo> getAllBuyerInfo(Long productId) {
        List<Transact> transacts = transactRepository.findByProductId(productId);
        Map<Long, List<TransactState>> buyerTransactStates = getBuyerTransactStates(transacts);

        return getPurchaseInfos(buyerTransactStates);
    }

    private LinkedHashMap<Long, List<TransactState>> getBuyerTransactStates(List<Transact> transacts) {
        return transactLogRepository.findAllByTransactIn(transacts)
                .stream()
                .sorted(Comparator.comparing(TransactLog::getId).reversed())
                .collect(Collectors.groupingBy(transactLog -> transactLog.getTransact().getBuyer().getId(),
                        LinkedHashMap::new,
                        Collectors.mapping(TransactLog::getTransactState, Collectors.toList())));
    }

    private static List<PurchaseInfo> getPurchaseInfos(Map<Long, List<TransactState>> buyerTransactStates) {
        return buyerTransactStates.entrySet()
                .stream()
                .map(entry -> new PurchaseBuyerResponse(entry.getKey(), entry.getValue()))
                .map(PurchaseInfo.class::cast)
                .toList();
    }

    private List<PurchaseInfo> getAllTransactStateInfo(Long productId, Long buyerId) {
        return transactRepository.retrieveAllTransactState(buyerId, productId)
                .stream()
                .map(PurchaseDetailResponse::new)
                .map(PurchaseInfo.class::cast)
                .toList();
    }

    public Page<PurchaseProductResponse> purchaseProducts(Long buyerId, Pageable pageable) {
        return productRepository.retrievePurchaseProducts(buyerId, pageable);
    }

    public Page<ReserveProductResponse> reserveProducts(Long sellerId, Pageable pageable) {
        return productRepository.retrieveReserveProducts(sellerId, pageable);
    }

    public void updatePrice(Long productId, int price, Long sellerId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ExceptionStatus.NOT_FOUND));

        if (!product.getSeller().getId().equals(sellerId)) {
            throw new CustomException(ExceptionStatus.NOT_SELLER);
        }

        product.updatePrice(new Price(price));
    }
}
