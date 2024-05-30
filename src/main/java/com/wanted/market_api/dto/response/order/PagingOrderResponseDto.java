package com.wanted.market_api.dto.response.order;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class PagingOrderResponseDto {
    int totalPages;
    long totalElements;
    int pageNumber;
    List<OrderResponseDto> orders;
}
