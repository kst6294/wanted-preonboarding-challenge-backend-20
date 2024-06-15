package com.sunyesle.wanted_market.offer;

import com.sunyesle.wanted_market.offer.dto.CreateOfferRequest;
import com.sunyesle.wanted_market.offer.dto.OfferDetailResponse;
import com.sunyesle.wanted_market.offer.dto.OfferResponse;
import com.sunyesle.wanted_market.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/offers")
@RequiredArgsConstructor
public class OfferController {
    private final OfferService offerService;

    @PostMapping
    public ResponseEntity<OfferResponse> offer(@AuthenticationPrincipal CustomUserDetails member, @RequestBody CreateOfferRequest request) {
        OfferResponse response = offerService.offer(member.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<OfferResponse> accept(@AuthenticationPrincipal CustomUserDetails member, @PathVariable Long id){
        OfferResponse response = offerService.accept(member.getId(), id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}/decline")
    public ResponseEntity<OfferResponse> decline(@AuthenticationPrincipal CustomUserDetails member, @PathVariable Long id){
        OfferResponse response = offerService.decline(member.getId(), id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<OfferResponse> confirm(@AuthenticationPrincipal CustomUserDetails member, @PathVariable Long id){
        OfferResponse response = offerService.confirm(member.getId(), id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<List<OfferDetailResponse>> offers(@AuthenticationPrincipal CustomUserDetails member){
        List<OfferDetailResponse> response = offerService.getOffers(member.getId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/received")
    public ResponseEntity<List<OfferDetailResponse>> receivedOffers(@AuthenticationPrincipal CustomUserDetails member){
        List<OfferDetailResponse> response = offerService.getReceivedOffers(member.getId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
