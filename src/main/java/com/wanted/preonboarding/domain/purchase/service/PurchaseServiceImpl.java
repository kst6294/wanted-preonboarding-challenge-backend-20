package com.wanted.preonboarding.domain.purchase.service;

import com.wanted.preonboarding.domain.product.entity.Product;
import com.wanted.preonboarding.domain.product.entity.ProductState;
import com.wanted.preonboarding.domain.product.repository.ProductRepository;
import com.wanted.preonboarding.domain.purchase.repository.PurchaseRepository;
import com.wanted.preonboarding.domain.purchase.dto.request.PurchaseRequest;
import com.wanted.preonboarding.domain.purchase.entity.Purchase;
import com.wanted.preonboarding.domain.purchase.entity.PurchaseState;
import com.wanted.preonboarding.domain.user.entity.User;
import com.wanted.preonboarding.domain.user.repository.UserRepository;
import com.wanted.preonboarding.global.exception.entity.RestApiException;
import com.wanted.preonboarding.global.exception.errorCode.ProductErrorCode;
import com.wanted.preonboarding.global.exception.errorCode.PurchaseErrorCode;
import com.wanted.preonboarding.global.exception.errorCode.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService{

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PurchaseRepository purchaseRepository;

    @Override
    @Transactional
    public void purchase(Long userId, PurchaseRequest purchaseRequest) {
        // 1. 유저, 제품 객체 가져오기
        User user = userRepository.findById(userId).orElseThrow(() -> new RestApiException(UserErrorCode.USER_NOT_FOUND, "유저를 찾을 수 없습니다. 아이디: "+userId));
        Product product = productRepository.findByIdWithLock(purchaseRequest.getId()).orElseThrow(() -> new RestApiException(ProductErrorCode.PRODUCT_NOT_FOUND, "상품을 조회할 수 없습니다. 아이디: "+purchaseRequest.getId()));

        // 2. 상품 소유자 / 재고 / 구매이력 확인
        if (product.getUser().getId() == user.getId()) throw new RestApiException(PurchaseErrorCode.PRODUCT_SELLER_SAME_CUSTOMER, "상품 판매자와 구매자가 동일합니다. 구매자 아이디: "+userId+" 상품 아이디: "+product.getId());
        else if (!product.getState().equals(ProductState.SALE)) throw new RestApiException(PurchaseErrorCode.PRODUCT_STATE_IS_NOT_SALE, "상품이 구매가능한 상태가 아닙니다. 상품 아이디: "+product.getId());
        else if (purchaseRepository.existsByUserAndProduct(user, product)) throw new RestApiException(PurchaseErrorCode.ALREADY_PURCHASED_PRODUCT, "이미 구매한 상품입니다. 구매자 아이디: "+userId+" 상품 아이디: "+product.getId());

        // 3. 상품 구입
        Purchase purchase = Purchase.builder()
                .price(product.getPrice())
                .state(PurchaseState.PENDING)
                .product(product)
                .user(user)
                .build();

        purchaseRepository.save(purchase);
        product.purchase();

        // 4. 상품 상태 확인
        if (product.getQuantity() == 0) product.updateState(ProductState.RESERVATION);

        productRepository.save(product);
    }

    @Override
    @Transactional
    public void accept(Long userId, PurchaseRequest purchaseRequest) {
        // 1. 유저, 제품 객체 가져오기
        User user = userRepository.findById(userId).orElseThrow(() -> new RestApiException(UserErrorCode.USER_NOT_FOUND, "유저를 찾을 수 없습니다. 아이디: "+userId));
        Purchase purchase = purchaseRepository.findByIdWithLock(purchaseRequest.getId()).orElseThrow(() -> new RestApiException(PurchaseErrorCode.PURCHASE_NOT_FOUND, "구매이력을 조회할 수 없습니다. 아이디: "+purchaseRequest.getId()));

        // 2. 상품 판매자 / 상태
        if (purchase.getProduct().getUser().getId() != user.getId()) throw new RestApiException(PurchaseErrorCode.SELLER_MISMATCH, "상품 판매자와 로그인 유저가 일치하지 않습니다. 로그인 아이디: "+userId+" 판매자 아이디: "+purchase.getProduct().getUser().getId());
        else if (!purchase.getState().equals(PurchaseState.PENDING)) throw new RestApiException(PurchaseErrorCode.ALREADY_APPROVED_PURCHASE, "이미 판매 승인한 주문입니다. 주문 아이디: "+purchaseRequest.getId());

        // 3. 상태 변경
        purchase.updateState(PurchaseState.ACCEPT_SALE);
        purchaseRepository.save(purchase);
    }
}
