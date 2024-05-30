package com.example.wantedmarketapi.controller;

import com.example.wantedmarketapi.annotation.auth.AuthMember;
import com.example.wantedmarketapi.common.BaseResponse;
import com.example.wantedmarketapi.converter.TradeConverter;
import com.example.wantedmarketapi.domain.member.Member;
import com.example.wantedmarketapi.dto.response.TradeResponseDto.*;
import com.example.wantedmarketapi.service.TradeCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/trades")
@Tag(name = "📋 Trade", description = "구매 관련 API")
public class TradeController {

    private final TradeCommandService tradeCommandService;

    @Operation(summary = "구매 생성 API", description = "구매를 생성합니다")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "성공")})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{productId}")
    public BaseResponse<CreateTradeResponse> createTrade(@Parameter(hidden = true) @AuthMember Member member, @PathVariable(name = "productId") Long productId){
        return BaseResponse.onSuccess(TradeConverter.toCreateTradeResponse(tradeCommandService.createTrade(member, productId)));
    }

}
