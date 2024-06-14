package org.example.wantedmarket.dto.product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.wantedmarket.dto.user.UserResponse;

@Getter
@Setter
@Builder
public class ProductCreateResponse {

    private ProductResponse product;
    private UserResponse seller;

}
