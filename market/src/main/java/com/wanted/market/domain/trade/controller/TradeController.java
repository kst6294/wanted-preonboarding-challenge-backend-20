package com.wanted.market.domain.trade.controller;

import com.wanted.market.domain.trade.request.TradeRequest;
import com.wanted.market.domain.trade.response.DetailResponse;
import com.wanted.market.domain.trade.response.ListResponse;
import com.wanted.market.domain.trade.service.TradeService;
import com.wanted.market.global.common.code.ResponseCode;
import com.wanted.market.domain.trade.entity.TradeStatusCode;
import com.wanted.market.global.common.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trade")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @PostMapping("/request")
    public BaseResponse requestTrade(@RequestBody @Valid TradeRequest request, BindingResult result) {
        tradeService.requestTrade(request);
        return new BaseResponse(ResponseCode.SUCCESS);
    }

    @PostMapping("/approve")
    public BaseResponse acceptTrade(@RequestBody @Valid TradeRequest request, BindingResult result) {
        tradeService.acceptTrade(request);
        return new BaseResponse(ResponseCode.SUCCESS);
    }

    @PostMapping("/complete")
    public BaseResponse completeTrade(@RequestBody @Valid TradeRequest request, BindingResult result) {
        tradeService.completeTrade(request);
        return new BaseResponse(ResponseCode.SUCCESS);
    }

    @GetMapping("/{product_no}")
    public BaseResponse getTrades(@PathVariable("product_no") int productNo) {
        return new ListResponse(ResponseCode.SUCCESS, tradeService.getTrades(productNo));
    }

    @GetMapping("/purchased/{page}")
    public BaseResponse getPurchasedTrades(@PathVariable("page") int page) {

        if (page < 0) {
            return new BaseResponse(ResponseCode.BAD_REQUEST);
        }

        return new ListResponse(ResponseCode.SUCCESS, tradeService.getPurchasedTrades(page - 1));
    }

    @GetMapping("/{state}/{page}")
    public BaseResponse getReservedTrades(@PathVariable("page") int page, @PathVariable("state") String state) {

        if (page < 0) {
            return new BaseResponse(ResponseCode.BAD_REQUEST);
        }

        if (! TradeStatusCode.isAvailable(state)) {
            return new BaseResponse(ResponseCode.BAD_REQUEST);
        }

        return new ListResponse(ResponseCode.SUCCESS, tradeService.getReservedTrades(page - 1, state));
    }

    @GetMapping("/detail/{product_no}")
    public BaseResponse getTrade(@PathVariable("product_no") long productNo) {

        if(productNo < 0) {
         return new BaseResponse(ResponseCode.BAD_REQUEST);
        }

        return new DetailResponse(ResponseCode.SUCCESS, tradeService.getTrade(productNo));
    }
}
