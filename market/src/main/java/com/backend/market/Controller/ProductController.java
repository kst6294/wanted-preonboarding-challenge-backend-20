package com.backend.market.Controller;


import com.backend.market.Common.BaseResponseBody;
import com.backend.market.DAO.Entity.Product;
import com.backend.market.Request.ProductReq;
import com.backend.market.Service.Product.ProductService;
import com.backend.market.Service.purchaseList.PurchaseListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private ProductService productService;
    private PurchaseListService purchaseListService;

    @GetMapping("/list")
    public ResponseEntity<?> list()
    {
        List<Product> lists = this.productService.getList();

        return ResponseEntity.status(200).body(lists);
    }


    @GetMapping("/info/{product_id}")
    public ResponseEntity<?> getProductInfo(@PathVariable Long product_id) throws Exception {
        Product product = this.productService.getProduct(product_id);

        return ResponseEntity.status(200).body(product);
    }

    @GetMapping("/reservation/{product_id}")
    public ResponseEntity<?> getReservationOfProducts(@PathVariable Long product_id) throws Exception {
        List<Product> product = this.productService.getReservationsList(product_id);

        return ResponseEntity.status(200).body(product);
    }

    @GetMapping("/complete/{product_id}")
    public ResponseEntity<?> getCompleteOfProducts(@PathVariable Long product_id) throws Exception {
        List<Product> product = this.productService.getBoughtList(product_id);

        return ResponseEntity.status(200).body(product);
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody ProductReq productReq) {
        if (productService.updateStatus(productReq)) {
            return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        }
        return new ResponseEntity<String>("FAIL", HttpStatus.NO_CONTENT);
    }

    @PostMapping("/add/{buy_id}")
    public ResponseEntity<?> createPurchaseList(@PathVariable Long buy_id, @RequestBody ProductReq productReq){

        if(Objects.equals(productReq.getMember().getUserId(), buy_id))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"구입 권한이 없습니다.");

        purchaseListService.addPurchasList(productReq,buy_id);

        return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);


    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductReq productReq){

        productService.addProduct(productReq);

        return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);


    }
}
