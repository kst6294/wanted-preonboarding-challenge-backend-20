package com.sunyesle.wanted_market.service;

import com.sunyesle.wanted_market.dto.CreateOfferRequest;
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
        product.buy(request.getQuantity());

        Offer offer = Offer.builder()
                .productId(product.getId())
                .sellerId(product.getMemberId())
                .buyerId(memberId)
                .price(product.getPrice())
                .quantity(request.getQuantity())
                .status(OfferStatus.OPEN)
                .build();
        offerRepository.save(offer);

        return new OfferResponse(offer.getId(), offer.getStatus());
    }

    public OfferResponse accept(Long memberId, Long offerId) {
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new ErrorCodeException(OfferErrorCode.OFFER_NOT_FOUND));
        if (!offer.getSellerId().equals(memberId)) {
            throw new ErrorCodeException(OfferErrorCode.NOT_OFFER_OFFEREE);
        }
        offer.setStatus(OfferStatus.ACCEPTED);

        Product product = productRepository.findById(offer.getProductId()).orElseThrow(() -> new ErrorCodeException(ProductErrorCode.PRODUCT_NOT_FOUND));

        return new OfferResponse(offer.getId(), offer.getStatus());
    }

    public OfferResponse decline(Long memberId, Long offerId) {
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new ErrorCodeException(OfferErrorCode.OFFER_NOT_FOUND));
        if (!offer.getSellerId().equals(memberId)) {
            throw new ErrorCodeException(OfferErrorCode.NOT_OFFER_OFFEREE);
        }
        offer.setStatus(OfferStatus.DECLINED);

        Product product = productRepository.findById(offer.getProductId()).orElseThrow(() -> new ErrorCodeException(ProductErrorCode.PRODUCT_NOT_FOUND));
        product.decline(offer.getQuantity());

        return new OfferResponse(offer.getId(), offer.getStatus());
    }

    public OfferResponse confirm(Long memberId, Long offerId) {
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new ErrorCodeException(OfferErrorCode.OFFER_NOT_FOUND));
        if (!offer.getBuyerId().equals(memberId)) {
            throw new ErrorCodeException(OfferErrorCode.NOT_OFFER_OFFEROR);
        }
        offer.setStatus(OfferStatus.CONFIRMED);

        Product product = productRepository.findById(offer.getProductId()).orElseThrow(() -> new ErrorCodeException(ProductErrorCode.PRODUCT_NOT_FOUND));
        List<Offer> confirmedOfferList = offerRepository.findByProductIdAndStatus(product.getId(), OfferStatus.CONFIRMED);
        Integer confirmedQuantity = confirmedOfferList.stream().mapToInt(Offer::getQuantity).sum();
        if(confirmedQuantity + offer.getQuantity() >= product.getQuantity()){
            product.complete();
        }

        return new OfferResponse(offer.getId(), offer.getStatus());
    }
}
