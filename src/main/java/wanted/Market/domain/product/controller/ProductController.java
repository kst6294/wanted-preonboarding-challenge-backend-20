package wanted.Market.domain.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import wanted.Market.domain.product.dto.ProductAddRequest;
import wanted.Market.domain.product.entity.Product;
import wanted.Market.domain.product.service.ProductService;
import wanted.Market.global.auth.CustomMemberDetails;
import wanted.Market.global.common.ResponseDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    //제품 등록
    @PostMapping
    public ResponseEntity<ResponseDto<?>> addProduct(@AuthenticationPrincipal CustomMemberDetails customMemberDetails, @RequestBody ProductAddRequest productAddRequest) {
        return ResponseEntity.status(200).body(ResponseDto.of("제품 등록 성공", productService.addProduct(productAddRequest, customMemberDetails.getUsername())));
    }
    //전체 조회
    @GetMapping
    public ResponseEntity<ResponseDto<?>> getAllProduct(){
        return ResponseEntity.status(200).body(ResponseDto.of("조회 성공", productService.getList()));
    }
    //상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> getProduct( @PathVariable("id") long id){
        return ResponseEntity.status(200).body(ResponseDto.of("조회 성공", productService.getProduct(id)));
    }

    //거래 진행(생성)
    @GetMapping("/{id}/Transaction")
    public ResponseEntity<ResponseDto<?>> createTransaction(
        @AuthenticationPrincipal CustomMemberDetails customMemberDetails,
        @PathVariable("id") long id
    ){
        return ResponseEntity.status(200).body(ResponseDto.of("거래 생성 성공",productService.purchaseProduct(customMemberDetails.getUsername(), id)));
    }
}