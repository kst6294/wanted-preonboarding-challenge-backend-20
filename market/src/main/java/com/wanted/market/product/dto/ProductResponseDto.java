package com.wanted.market.product.dto;


import com.wanted.market.member.dto.MemberResponseDto;
import com.wanted.market.product.domain.Product;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {

    private Integer id;
    private String productName;
    private Integer price;
    private Integer quantity;
    private String status;
    private MemberResponseDto seller;

    public static ProductResponseDto createFromEntity(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .productName(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .status(product.getStatus().toString())
                .seller(MemberResponseDto.createFromEntity(product.getSeller()))
                .build();
    }

}
