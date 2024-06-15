package com.sunyesle.wanted_market.service;

import com.sunyesle.wanted_market.dto.CreateOfferRequest;
import com.sunyesle.wanted_market.dto.OfferDetailResponse;
import com.sunyesle.wanted_market.dto.OfferResponse;
import com.sunyesle.wanted_market.entity.Offer;
import com.sunyesle.wanted_market.entity.Product;
import com.sunyesle.wanted_market.enums.OfferStatus;
import com.sunyesle.wanted_market.exception.ErrorCodeException;
import com.sunyesle.wanted_market.exception.OfferErrorCode;
import com.sunyesle.wanted_market.exception.ProductErrorCode;
import com.sunyesle.wanted_market.repository.OfferRepository;
import com.sunyesle.wanted_market.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OfferService {
    private final ProductRepository productRepository;
    private final OfferRepository offerRepository;

    public OfferResponse offer(Long memberId, CreateOfferRequest request) {
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new ErrorCodeException(OfferErrorCode.OFFER_NOT_FOUND));
        if (offerRepository.existsByProductIdAndBuyerIdAndStatus(product.getId(), memberId, OfferStatus.OPEN)) {
            throw new ErrorCodeException(OfferErrorCode.DUPLICATE_OFFER);
        }

        Offer offer = new Offer(product.getId(), memberId, product.getPrice(), request.getQuantity());
        offerRepository.save(offer);

        return new OfferResponse(offer.getId(), offer.getStatus());
    }

    public OfferResponse accept(Long memberId, Long offerId) {
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new ErrorCodeException(OfferErrorCode.OFFER_NOT_FOUND));
        Product product = productRepository.findById(offer.getProductId()).orElseThrow(() -> new ErrorCodeException(ProductErrorCode.PRODUCT_NOT_FOUND));

        if (!memberId.equals(product.getMemberId())) {
            throw new ErrorCodeException(OfferErrorCode.NOT_OFFER_OFFEREE);
        }
        offer.accept(memberId);
        product.reserve(offer.getQuantity());

        return new OfferResponse(offer.getId(), offer.getStatus());
    }

    public OfferResponse decline(Long memberId, Long offerId) {
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new ErrorCodeException(OfferErrorCode.OFFER_NOT_FOUND));
        Product product = productRepository.findById(offer.getProductId()).orElseThrow(() -> new ErrorCodeException(ProductErrorCode.PRODUCT_NOT_FOUND));

        if (!memberId.equals(product.getMemberId())) {
            throw new ErrorCodeException(OfferErrorCode.NOT_OFFER_OFFEREE);
        }
        offer.decline(memberId);

        return new OfferResponse(offer.getId(), offer.getStatus());
    }

    public OfferResponse confirm(Long memberId, Long offerId) {
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new ErrorCodeException(OfferErrorCode.OFFER_NOT_FOUND));
        Product product = productRepository.findById(offer.getProductId()).orElseThrow(() -> new ErrorCodeException(ProductErrorCode.PRODUCT_NOT_FOUND));

        offer.confirm(memberId);
        product.purchase(offer.getQuantity());

        return new OfferResponse(offer.getId(), offer.getStatus());
    }

    public List<OfferDetailResponse> getOffers(Long memberId) {
        List<Offer> offers = offerRepository.findByBuyerId(memberId);
        return offers.stream().map(OfferDetailResponse::of).toList();
    }

    public List<OfferDetailResponse> getReceivedOffers(Long memberId) {
        List<Offer> offers = offerRepository.findReceivedOffers(memberId);
        return offers.stream().map(OfferDetailResponse::of).toList();
    }
}
