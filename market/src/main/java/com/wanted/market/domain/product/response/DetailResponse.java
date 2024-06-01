package com.wanted.market.domain.product.response;

import com.wanted.market.domain.product.entity.Product;
import com.wanted.market.domain.product.entity.ProductStatusCode;
import com.wanted.market.global.common.code.ResponseCode;
import com.wanted.market.global.common.response.BaseResponse;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DetailResponse extends BaseResponse {

    private Long productNo;

    private long sellerMemberNo;

    private String sellerMemberName;

    private String name;

    private Long price;

    private String description;

    private ProductStatusCode productStatus;

    private LocalDateTime registerDate;

    private LocalDateTime updateDate;

    public DetailResponse(ResponseCode response, Product product) {
        super(response);
        this.productNo = product.getProductNo();
        this.sellerMemberNo = product.getSeller().getMemberNo();
        this.sellerMemberName = product.getSeller().getName();
        this.name = product.getName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.productStatus = product.getStatus();
        this.registerDate = product.getRegisterDate();
        this.updateDate = product.getUpdateDate();
    }

    public DetailResponse(ResponseCode response) {
        super(response);
    }
}
