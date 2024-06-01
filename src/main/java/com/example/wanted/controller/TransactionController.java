package com.example.wanted.controller;

import com.example.wanted.config.auth.PrincipalDetails;
import com.example.wanted.model.Product;
import com.example.wanted.model.State;
import com.example.wanted.model.Transaction;
import com.example.wanted.repository.ProductRepository;
import com.example.wanted.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/transaction/*")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ProductRepository productRepository;

//    @GetMapping("list/{id}")
//    public List<Transaction> list(@PathVariable Long id) {
//        System.out.println("product_id  :  " + id);
//        List<Transaction> orderList = orderService.list(id);
//        return orderList;
//    }

    @PostMapping("insert/{id}")
    @Tag(name = "Transaction Register", description = "Transaction Register API")
    @Operation(summary = "거래 등록", description = "거래 등록 시 사용하는 API")
    public ResponseEntity<String> orderInsert(@PathVariable Long id,
                                              Transaction transaction,
                                              @AuthenticationPrincipal PrincipalDetails principal) {
        System.out.println("principal.order.insert : " + principal);
        Product product = productRepository.findById(id).get();
        product.setState(State.RESERVED);
        transaction.setProduct(product);
        transaction.setUser(principal.getUser());//user

        transactionService.insert(transaction);
        System.out.println("추가성공" + product);
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }
}
