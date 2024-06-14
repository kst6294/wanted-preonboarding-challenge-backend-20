package com.example.wanted.controller;

import com.example.wanted.config.auth.PrincipalDetails;
import com.example.wanted.model.Product;
import com.example.wanted.model.Transaction;
import com.example.wanted.service.TransactionService;
import com.example.wanted.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
    @Tag(name = "Product Register", description = "Product Register API")
    @Operation(summary = "판매자 판매 물품 등록", description = "판매자 판매 물품 등록 API")
    public String insert(Product product, @AuthenticationPrincipal PrincipalDetails principal) {
        productService.insert(product, principal.getUser());
        return "redirect:productList";
    }

    //물품 상세보기
    @GetMapping("productDetail/{p_id}")
    @Tag(name = "Product Detail", description = "Product Detail API")
    @Operation(summary = "물품 상세보기", description = "물품 상세보기 API")
    @Parameter(name = "p_id", description = "물품 번호", example = "1")
    public String detail(@PathVariable Long p_id, Model model) {
        model.addAttribute("sell", transactionService.sellList(p_id));
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
    public String update(@RequestBody ObjectNode saveObj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Product product = mapper.treeToValue(saveObj.get("product"),Product.class);
        Transaction transaction = mapper.treeToValue(saveObj.get("transaction"),Transaction.class);
        System.out.println("거래 승인 성공"+product.getP_id());
        Product newProduct = productService.findById(product.getP_id());
        productService.update(newProduct);
        transactionService.update(transaction);
        return "success";
    }
//    public String update(@RequestParam("p_id") Long p_id,@RequestParam("t_id") Long t_id) {
//
//        System.out.println("아이디"+ p_id);
//        System.out.println("아이디"+ t_id);
//        Product product = productService.findById(p_id);
//        productService.update(product);
//        return "success";
//    }
}
