package com.chaewon.wanted.domain.orders.dto.response;

import com.chaewon.wanted.domain.member.entity.Member;
import com.chaewon.wanted.domain.product.entity.Product;
import com.chaewon.wanted.domain.product.entity.ProductStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SalesApprovalListResponseDto {

    private Long productId;
    private int price;
    private ProductStatus productStatus;

    public static SalesApprovalListResponseDto from(Product product, Member member) {
        return SalesApprovalListResponseDto.builder()
                .productId(product.getId())
                .price(product.getPrice())
                .productStatus(product.getProductStatus())
                .build();
    }
}

