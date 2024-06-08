package com.wanted.challenge.domain.item.dto.request;

import com.wanted.challenge.domain.item.entity.Item;
import com.wanted.challenge.domain.item.entity.SaleStatus;
import com.wanted.challenge.domain.member.entity.Member;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemRegisterRequestDTO {

    @NotBlank(message = "상품 이름을 입력해주세요.")
    @Size(min = 2, max = 30, message = "상품 이름은 2~30자까지 가능합니다.")
    private String name;

    @NotNull(message = "가격을 입력해주세요.")
    @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
    @Max(value = 2100000000, message = "가격은 21억까지 가능합니다.")
    private Long price;

    @NotNull(message = "수량을 입력해주세요.")
    @Min(value = 1, message = "수량은 0 이상이어야 합니다.")
    @Max(value = 9999, message = "수량은 최대 9999까지 가능합니다.")
    private Integer quantity;

    public Item toEntity(Member member){
        return Item.builder()
                .member(member)
                .name(name)
                .price(price)
                .quantity(quantity)
                .saleStatus(SaleStatus.FOR_SALE).build();
    }
}
