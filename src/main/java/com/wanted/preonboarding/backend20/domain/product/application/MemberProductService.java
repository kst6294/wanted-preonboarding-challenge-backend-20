package com.wanted.preonboarding.backend20.domain.product.application;

import com.wanted.preonboarding.backend20.domain.member.domain.Member;
import com.wanted.preonboarding.backend20.domain.order.enums.OrderStatus;
import com.wanted.preonboarding.backend20.domain.product.dto.ProductOutlineDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberProductService {
    Page<ProductOutlineDto> searchAllMyProductStatusReserved(Member member, Pageable pageable);
    Page<ProductOutlineDto> searchAllProductMemberOrder(OrderStatus orderStatus, Member member, Pageable pageable);
}
