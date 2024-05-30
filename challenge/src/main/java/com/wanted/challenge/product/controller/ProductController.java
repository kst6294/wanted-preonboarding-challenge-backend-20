package com.wanted.challenge.product.controller;

import com.wanted.challenge.account.model.AccountDetail;
import com.wanted.challenge.product.model.Price;
import com.wanted.challenge.product.request.PurchaseRequest;
import com.wanted.challenge.product.request.RegisterRequest;
import com.wanted.challenge.product.service.ProductService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest registerRequest,
                                         @AuthenticationPrincipal AccountDetail accountDetail) {

        Price price = new Price(registerRequest.price());
        Long productId = productService.register(registerRequest.name(), price, accountDetail);

        return ResponseEntity.created(URI.create("/products/" + productId))
                .build();
    }

    @PostMapping("/purchase")
    public ResponseEntity<Void> purchase(@RequestBody @Valid PurchaseRequest purchaseRequest,
                                         @AuthenticationPrincipal AccountDetail accountDetail) {
        productService.purchase(purchaseRequest.productId(), accountDetail);

        return ResponseEntity.ok()
                .build();
    }
}
