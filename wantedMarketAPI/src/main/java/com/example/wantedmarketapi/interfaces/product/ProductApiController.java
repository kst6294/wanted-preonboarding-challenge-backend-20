package com.example.wantedmarketapi.interfaces.product;


import com.example.wantedmarketapi.application.product.ProductFacade;
import com.example.wantedmarketapi.domain.product.ProductCommand;
import com.example.wantedmarketapi.domain.product.ProductInfo;
import com.example.wantedmarketapi.interfaces.product.ProductDto.RegisterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductApiController {

    private final ProductFacade productFacade;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid ProductDto.RegisterRequest request) {
        ProductCommand command = request.toCommand();
        ProductInfo productInfo = productFacade.registerProduct(command);
        RegisterResponse response = new RegisterResponse(productInfo);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

}
