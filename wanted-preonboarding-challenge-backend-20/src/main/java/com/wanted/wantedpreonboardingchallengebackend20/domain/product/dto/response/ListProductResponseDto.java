package com.wanted.wantedpreonboardingchallengebackend20.domain.product.dto.response;

import com.wanted.wantedpreonboardingchallengebackend20.domain.product.entity.ProductState;
import lombok.*;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ListProductResponseDto {
    private List<ProductDto> productDtoList;
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ProductDto{
        private String productName;
        private ProductState state;
        private int price;
        private int quantity;
        private UserDto seller;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserDto{
        private String userName;
    }


}
