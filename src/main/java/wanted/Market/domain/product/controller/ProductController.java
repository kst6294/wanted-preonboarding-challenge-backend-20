package wanted.Market.domain.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wanted.Market.domain.product.dto.ProductAddRequest;
import wanted.Market.domain.product.entity.Product;
import wanted.Market.domain.product.service.ProductService;
import wanted.Market.global.common.ResponseDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ResponseDto<Product>> addProduct(@RequestBody ProductAddRequest productAddRequest) {
        return ResponseEntity.status(200).body(ResponseDto.of("제품 등록 성공", productService.addProduct(productAddRequest)));
    }
    @GetMapping
    public ResponseEntity<ResponseDto<List<Product>>> getAllProduct(){
        return ResponseEntity.status(200).body(ResponseDto.of("조회 성공", productService.getList()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<Product>> getProduct( @PathVariable("id") long id){
        return ResponseEntity.status(200).body(ResponseDto.of("조회 성공", productService.getProduct(id)));
    }
}