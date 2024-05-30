package com.exception_study.dto.response;

import com.exception_study.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class HistoryResponse {
    private List<ProductDto> buy_history;
    private List<ProductDto> reserve_history;


    public static HistoryResponse of(List<ProductDto> buy_history, List<ProductDto> reserve_history){
        return new HistoryResponse(buy_history,reserve_history);
    }
}
