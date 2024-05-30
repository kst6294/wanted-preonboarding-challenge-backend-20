package com.exception_study.dto.response;

import com.exception_study.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class DetailsWithHistoryResponse {
    private ProductDto product;
    private List<ProductDto> history;


    public static DetailsWithHistoryResponse of(ProductDto product, List<ProductDto> history){
        return new DetailsWithHistoryResponse(product,history);
    }
}
