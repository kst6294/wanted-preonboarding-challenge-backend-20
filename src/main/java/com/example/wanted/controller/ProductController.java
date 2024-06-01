package com.example.wanted.controller;

import com.example.wanted.config.auth.PrincipalDetails;
import com.example.wanted.model.Product;
import com.example.wanted.service.TransactionService;
import com.example.wanted.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product/*")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private TransactionService transactionService;

    @GetMapping("productList")
    public String list(Model model, @PageableDefault(size = 15, direction = Sort.Direction.DESC) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String field,
                       @RequestParam(required = false, defaultValue = "") String word) {
        Page<Product> lists = productService.findAll(field, word, pageable);
        model.addAttribute("lists", lists);
        return "/product/productList";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("productInsert")
    public String insert() {
        return "/product/productInsert";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("productInsert")
    public String insert(Product product, @AuthenticationPrincipal PrincipalDetails principal) {
        productService.insert(product, principal.getUser());
        return "redirect:productList";
    }

    //물품 상세보기
    @GetMapping("productDetail/{p_id}")
    public String detail(@PathVariable Long p_id, Model model) {
        model.addAttribute("buyer", transactionService.findByBuyerId(p_id));
        model.addAttribute("product", productService.findById(p_id));
        return "/product/productDetail";
    }

    @GetMapping("update/{p_id}")
    public String update(@PathVariable Long p_id, Model model) {
        model.addAttribute("product", productService.findById(p_id));
        return "/product/update";
    }

    @PutMapping("update")
    @ResponseBody
    @Tag(name = "Transaction Complete", description = "Transaction Complete API")
    @Operation(summary = "판매자 거래 승인", description = "판매자 거래 승인 시 사용하는 API")
    @Parameters({
            @Parameter(name = "t_id", description = "거래번호", example = "1"),
            @Parameter(name = "user.email", description = "구매자", example = "hong@naver.com"),
            @Parameter(name = "seller_id", description = "판매자번호", example = "2"),
    })
    public String update(@RequestBody Product product) {
        productService.update(product);
        return "success";
    }
}
