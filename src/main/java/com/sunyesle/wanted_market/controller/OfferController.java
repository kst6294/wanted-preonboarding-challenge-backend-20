package com.sunyesle.wanted_market.controller;

import com.sunyesle.wanted_market.dto.OfferRequest;
import com.sunyesle.wanted_market.dto.OfferResponse;
import com.sunyesle.wanted_market.security.CustomUserDetails;
import com.sunyesle.wanted_market.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/offers")
@RequiredArgsConstructor
public class OfferController {
    private final OfferService offerService;

    @PostMapping
    public ResponseEntity<OfferResponse> offer(@AuthenticationPrincipal CustomUserDetails member, @RequestBody OfferRequest offerRequest){
        OfferResponse response = offerService.offer(member.getId(), offerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<OfferResponse> accept(@AuthenticationPrincipal CustomUserDetails member, @PathVariable Long id){
        OfferResponse response = offerService.accept(member.getId(), id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
