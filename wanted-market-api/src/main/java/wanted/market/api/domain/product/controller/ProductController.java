package wanted.market.api.domain.product.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wanted.market.api.domain.product.dto.request.ModifiedProductRequestDto;
import wanted.market.api.domain.product.dto.request.RegisterProductRequestDto;
import wanted.market.api.domain.product.dto.response.ModifiedProductResponseDto;
import wanted.market.api.domain.product.dto.response.ProductDetailResponseDto;
import wanted.market.api.domain.product.dto.response.ProductPageResponseDto;
import wanted.market.api.domain.product.dto.response.RegisterProductResponseDto;
import wanted.market.api.domain.product.service.ProductService;

import static wanted.market.api.domain.product.constants.ProductConstant.DEFAULT_STATUS_VALUE;
import static wanted.market.api.domain.product.constants.ProductConstant.NAME_OF_STATUS_PARAM;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("")
    public ResponseEntity<RegisterProductResponseDto> registerProduct(HttpServletRequest request, @RequestBody RegisterProductRequestDto requestDto) {
        return ResponseEntity.ok(productService.registerProduct(request, requestDto));
    }

    @GetMapping("")
    public ResponseEntity<ProductPageResponseDto> searchProductList(Pageable pageable,
                                                                    @RequestParam(required = false, defaultValue = DEFAULT_STATUS_VALUE, value = NAME_OF_STATUS_PARAM) String status) {
        return ResponseEntity.ok().body(productService.searchProductList(pageable, status));
    }

    @PutMapping("")
    public ResponseEntity<ModifiedProductResponseDto> modifiedProduct(HttpServletRequest request,
                                                                      @RequestBody ModifiedProductRequestDto requestDto){
        return ResponseEntity.ok().body(productService.modifyProduct(request, requestDto));
    }
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailResponseDto> searchProductDetail(@PathVariable Long productId) {
        return ResponseEntity.ok().body(productService.searchProductDetail(productId));
    }

}