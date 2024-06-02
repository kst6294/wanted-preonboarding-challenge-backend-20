package com.sunyesle.wanted_market.service;

import com.sunyesle.wanted_market.dto.OfferRequest;
import com.sunyesle.wanted_market.dto.OfferResponse;
import com.sunyesle.wanted_market.entity.Offer;
import com.sunyesle.wanted_market.entity.Product;
import com.sunyesle.wanted_market.enums.OfferStatus;
import com.sunyesle.wanted_market.repository.OfferRepository;
import com.sunyesle.wanted_market.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final ProductRepository productRepository;
    private final OfferRepository offerRepository;

    public OfferResponse offer(Long memberId, OfferRequest offerRequest) {
        Product product = productRepository.findById(offerRequest.getProductId()).orElseThrow(RuntimeException::new);
        Offer offer = Offer.builder()
                .productId(product.getId())
                .sellerId(product.getMemberId())
                .buyerId(memberId)
                .status(OfferStatus.OPEN)
                .build();
        offerRepository.save(offer);
        return new OfferResponse(offer.getId(), offer.getStatus());
    }
}
