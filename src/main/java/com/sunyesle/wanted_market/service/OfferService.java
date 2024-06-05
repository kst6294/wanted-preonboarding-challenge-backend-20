package com.sunyesle.wanted_market.service;

import com.sunyesle.wanted_market.dto.OfferRequest;
import com.sunyesle.wanted_market.dto.OfferResponse;
import com.sunyesle.wanted_market.entity.Offer;
import com.sunyesle.wanted_market.entity.Product;
import com.sunyesle.wanted_market.enums.OfferStatus;
import com.sunyesle.wanted_market.enums.ProductStatus;
import com.sunyesle.wanted_market.exception.ErrorCodeException;
import com.sunyesle.wanted_market.exception.OfferErrorCode;
import com.sunyesle.wanted_market.repository.OfferRepository;
import com.sunyesle.wanted_market.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OfferService {
    private final ProductRepository productRepository;
    private final OfferRepository offerRepository;

    public OfferResponse offer(Long memberId, OfferRequest offerRequest) {
        Product product = productRepository.findById(offerRequest.getProductId()).orElseThrow(() -> new ErrorCodeException(OfferErrorCode.OFFER_NOT_FOUND));
        product.setStatus(ProductStatus.RESERVED);

        Offer offer = Offer.builder()
                .productId(product.getId())
                .sellerId(product.getMemberId())
                .buyerId(memberId)
                .status(OfferStatus.OPEN)
                .build();
        offerRepository.save(offer);
        return new OfferResponse(offer.getId(), offer.getStatus());
    }

    public OfferResponse accept(Long memberId, Long offerId) {
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new ErrorCodeException(OfferErrorCode.OFFER_NOT_FOUND));
        if(!offer.getSellerId().equals(memberId)){
            throw new ErrorCodeException(OfferErrorCode.NOT_OFFER_OFFEREE);
        }
        offer.setStatus(OfferStatus.ACCEPTED);

        Product product = productRepository.findById(offer.getProductId()).orElseThrow(() -> new ErrorCodeException(OfferErrorCode.OFFER_NOT_FOUND));
        product.setStatus(ProductStatus.COMPLETED);

        return new OfferResponse(offer.getId(), offer.getStatus());
    }

    public OfferResponse decline(Long memberId, Long offerId) {
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new ErrorCodeException(OfferErrorCode.OFFER_NOT_FOUND));
        if(!offer.getSellerId().equals(memberId)){
            throw new ErrorCodeException(OfferErrorCode.NOT_OFFER_OFFEREE);
        }
        offer.setStatus(OfferStatus.DECLINED);

        Product product = productRepository.findById(offer.getProductId()).orElseThrow(RuntimeException::new);
        product.setStatus(ProductStatus.AVAILABLE);

        return new OfferResponse(offer.getId(), offer.getStatus());
    }
}
