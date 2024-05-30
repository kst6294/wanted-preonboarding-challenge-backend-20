package com.wanted.market_api.dto.response.product;


import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class PagingProductResponseDto {
    int totalPages;
    long totalElements;
    int pageNumber;
    List<ProductResponseDto> products;
}
