package com.example.wanted.controller;

import com.example.wanted.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/*")
public class UserController {
    //거래목록 확인 마이페이지
    @Autowired
    private OrderService orderService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("myPage/{id}")
    public String myPage(Model model, @PathVariable Long id) {
        model.addAttribute("buy", orderService.purchaseList(id));
        model.addAttribute("sell", orderService.sellList(id));
        System.out.println("마이 페이지 출력");
        return "user/myPage";
    }
}
