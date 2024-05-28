package com.wanted.preonboarding.backend20.domain.product.api;

import com.wanted.preonboarding.backend20.domain.member.domain.Member;
import com.wanted.preonboarding.backend20.domain.order.enums.OrderStatus;
import com.wanted.preonboarding.backend20.domain.product.application.MemberProductService;
import com.wanted.preonboarding.backend20.domain.product.dto.ProductOutlineDto;
import com.wanted.preonboarding.backend20.global.auth.AuthMember;
import com.wanted.preonboarding.backend20.global.exception.CustomException;
import com.wanted.preonboarding.backend20.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products/members")
@RequiredArgsConstructor
public class MemberProductController {

    private final MemberProductService productService;

    @GetMapping("/sale/reserved")
    public ResponseEntity<Page<ProductOutlineDto>> searchAllMyProductStatusReserved(@AuthMember Member member,
                                                                                    @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        if(member == null) throw new CustomException(ErrorCode.AUTHENTICATION_ERROR);
        return ResponseEntity.ok(productService.searchAllMyProductStatusReserved(member, pageable));
    }

    @GetMapping("/orders")
    public ResponseEntity<Page<ProductOutlineDto>> searchAllProductMemberOrder(@RequestParam(defaultValue = "PRE") OrderStatus orderStatus, @AuthMember Member member,
                                                                               @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        if(member == null) throw new CustomException(ErrorCode.AUTHENTICATION_ERROR);
        return ResponseEntity.ok(productService.searchAllProductMemberOrder(orderStatus, member, pageable));
    }
}
