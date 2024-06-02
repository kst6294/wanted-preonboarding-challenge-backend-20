package com.backend.market.Controller;


import com.backend.market.Common.BaseResponseBody;
import com.backend.market.Common.auth.UserDetailsImpl;
import com.backend.market.DAO.Entity.Product;
import com.backend.market.Request.ProductReq;
import com.backend.market.Service.Product.ProductService;
import com.backend.market.Service.purchaseList.PurchaseListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private ProductService productService;

    @GetMapping("/list")
    public ResponseEntity<?> list()
    {
        List<Product> lists = this.productService.getList();

        return ResponseEntity.status(200).body(lists);
    }


    @GetMapping("/info/{product_id}")
    public ResponseEntity<?> getProductInfo(@PathVariable Long product_id
    , @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        Product product = this.productService.getProduct(product_id,userDetails);

        return ResponseEntity.status(200).body(product);
    }

    @GetMapping("/reservation/{product_id}")
    public ResponseEntity<?> getReservationOfProducts(@PathVariable Long product_id,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        List<Product> product = this.productService.getReservationsList(product_id,userDetails);

        return ResponseEntity.status(200).body(product);
    }

    @GetMapping("/complete/{product_id}")
    public ResponseEntity<?> getCompleteOfProducts(@PathVariable Long product_id,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        List<Product> product = this.productService.getBoughtList(product_id,userDetails);

        return ResponseEntity.status(200).body(product);
    }

    @PutMapping("/confirm")
    public ResponseEntity<?> confirmProduct(@RequestBody ProductReq productReq,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        productService.permitSaleForProduct(productReq,userDetails);
        return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody ProductReq productReq,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (productService.updateStatus(productReq,userDetails)) {
            return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        }
        return new ResponseEntity<String>("FAIL", HttpStatus.NO_CONTENT);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductReq productReq,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails){

        productService.addProduct(productReq,userDetails);

        return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    }
}
