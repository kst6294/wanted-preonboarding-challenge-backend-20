package com.example.wanted.controller;

import com.example.wanted.config.auth.PrincipalDetails;
import com.example.wanted.model.Product;
import com.example.wanted.model.State;
import com.example.wanted.model.Transaction;
import com.example.wanted.model.User;
import com.example.wanted.repository.ProductRepository;
import com.example.wanted.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order/*")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductRepository productRepository;

//    @GetMapping("list/{id}")
//    public List<Transaction> list(@PathVariable Long id) {
//        System.out.println("product_id  :  " + id);
//        List<Transaction> orderList = orderService.list(id);
//        return orderList;
//    }

    @PostMapping("insert/{id}")
    public ResponseEntity<String> orderInsert(@PathVariable Long id,
                                              Transaction transaction, @RequestParam(value = "u_id", required = false) Long u_id,
                                              @AuthenticationPrincipal PrincipalDetails principal) {
        System.out.println("principal.order.insert : " + principal);
        Product product = productRepository.findById(id).get();
        product.setState(State.RESERVED);
        transaction.setSeller_id(u_id);
        transaction.setProduct(product);
        transaction.setUser(principal.getUser());//user

        orderService.insert(transaction);
        System.out.println("추가성공" + product);
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }
}
