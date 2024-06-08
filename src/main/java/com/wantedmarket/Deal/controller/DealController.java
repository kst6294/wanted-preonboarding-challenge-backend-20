package com.wantedmarket.Deal.controller;

import com.wantedmarket.Deal.dto.DealDto;
import com.wantedmarket.Deal.facade.DealFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/deal")
@RequiredArgsConstructor
public class DealController {

    private final DealFacade dealFacade;

    @PostMapping("/purchase/{itemId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<DealDto> purchaseItem(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable(value = "itemId") Long id
    ) {
        return ResponseEntity.ok(dealFacade.purchase(userDetails.getUsername(), id));
    }

    @GetMapping("/transaction/{itemId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<DealDto>> getTransaction(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable(value = "itemId") Long id
    ) {
        return ResponseEntity.ok(dealFacade.getTransaction(userDetails.getUsername(), id));
    }

    @PatchMapping("/approve/{dealId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> approveTransaction(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable(value = "dealId") Long id
    ) {
        dealFacade.approveTransaction(userDetails.getUsername(), id);
        return ResponseEntity.ok("판매 승인되었습니다");
    }
}
