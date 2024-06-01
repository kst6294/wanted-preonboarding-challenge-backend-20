package com.example.wanted.controller;

import com.example.wanted.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private TransactionService transactionService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("myPage/{id}")
    @Tag(name = "User MyPage", description = "User MyPage API")
    @Operation(summary = "마이 페이지", description = "회원의 거래 내역(구매,판매 현황) API")
    @Parameter(name = "id", description = "회원 번호", example = "1", required = true)
    public String myPage(Model model, @PathVariable Long id) {
        model.addAttribute("buy", transactionService.purchaseList(id));
        model.addAttribute("sell", transactionService.sellList(id));
        System.out.println("마이 페이지 출력");
        return "user/myPage";
    }
}
