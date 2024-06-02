package com.wanted.demo.domain.product.dto.request;

import com.wanted.demo.domain.product.entity.Product;
import com.wanted.demo.domain.product.entity.State;
import com.wanted.demo.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductRegisterRequestDTO {

    private String name;
    private Long price;
    private Long quantity;

    public Product toEntity(User user){
        return Product.builder()
                .user(user)
                .name(name)
                .price(price)
                .quantity(quantity)
                .state(State.SELL).build();
    }

}
