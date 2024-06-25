package com.sunyesle.wanted_market.offer;

import com.sunyesle.wanted_market.global.enums.OfferStatus;
import com.sunyesle.wanted_market.global.exception.ErrorCodeException;
import com.sunyesle.wanted_market.global.exception.OfferErrorCode;
import com.sunyesle.wanted_market.global.exception.ProductErrorCode;
import com.sunyesle.wanted_market.offer.dto.CreateOfferRequest;
import com.sunyesle.wanted_market.offer.dto.OfferDetailResponse;
import com.sunyesle.wanted_market.offer.dto.OfferResponse;
import com.sunyesle.wanted_market.product.Product;
import com.sunyesle.wanted_market.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OfferService {
    private final ProductService productService;
    private final OfferRepository offerRepository;

    public OfferResponse offer(Long memberId, CreateOfferRequest request) {
        Product product = productService.findById(request.getProductId());
        if (!productService.checkAvailability(request.getProductId())) {
            throw new ErrorCodeException(ProductErrorCode.PRODUCT_NOT_AVAILABLE);
        }
        if (offerRepository.existsByProductIdAndBuyerIdAndStatus(request.getProductId(), memberId, OfferStatus.OPEN)) {
            throw new ErrorCodeException(OfferErrorCode.DUPLICATE_OFFER);
        }

        Offer offer = new Offer(request.getProductId(), memberId, product.getPrice(), request.getQuantity());
        offerRepository.save(offer);

        return new OfferResponse(offer.getId(), offer.getStatus());
    }

    public OfferResponse accept(Long memberId, Long offerId) {
        Offer offer = findById(offerId);
        if (!productService.checkSeller(offer.getProductId(), memberId)) {
            throw new ErrorCodeException(OfferErrorCode.NOT_OFFER_OFFEREE);
        }
        offer.accept();

        productService.makeReservation(offer.getProductId(), offer.getQuantity());
        return new OfferResponse(offer.getId(), offer.getStatus());
    }

    public OfferResponse decline(Long memberId, Long offerId) {
        Offer offer = findById(offerId);
        if (!productService.checkSeller(offer.getProductId(), memberId)) {
            throw new ErrorCodeException(OfferErrorCode.NOT_OFFER_OFFEREE);
        }
        offer.decline();

        return new OfferResponse(offer.getId(), offer.getStatus());
    }

    public OfferResponse confirm(Long memberId, Long offerId) {
        Offer offer = findById(offerId);
        if (!memberId.equals(offer.getBuyerId())) {
            throw new ErrorCodeException(OfferErrorCode.NOT_OFFER_OFFEROR);
        }
        offer.confirm();

        productService.placeOrder(offer.getProductId(), offer.getQuantity());
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

    private Offer findById(Long offerId) {
        return offerRepository
                .findById(offerId).orElseThrow(() -> new ErrorCodeException(OfferErrorCode.OFFER_NOT_FOUND));
    }
}
