package wanted.market.api.domain.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wanted.market.api.domain.product.dto.internal.ProductDto;
import wanted.market.api.domain.product.dto.request.RegisterProductRequestDto;
import wanted.market.api.domain.product.dto.response.ProductResponseDto;
import wanted.market.api.domain.product.dto.response.RegisterProductResponseDto;
import wanted.market.api.domain.product.service.ProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("")
    public ResponseEntity<RegisterProductResponseDto> registerProduct(@RequestBody RegisterProductRequestDto requestDto) {
        return ResponseEntity.ok(productService.registerProduct(requestDto));
    }

    @GetMapping("")
    public ResponseEntity<ProductResponseDto> getAllProducts(@RequestParam(required = false, defaultValue = "0", value = "page") int page,
                                                             Pageable pageable) {
        return ResponseEntity.ok().body(productService.getAllProducts(page));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long productId) {
        return ResponseEntity.ok().body(productService.getProduct(productId));
    }

}