package com.wanted.market.domain.trade.controller;

import com.wanted.market.domain.trade.request.TradeRequest;
import com.wanted.market.domain.trade.response.DetailResponse;
import com.wanted.market.domain.trade.response.ListResponse;
import com.wanted.market.domain.trade.service.TradeService;
import com.wanted.market.global.common.code.ResponseCode;
import com.wanted.market.global.common.response.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
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
    public BaseResponse requestTrade(@RequestBody @Valid TradeRequest request, BindingResult result, HttpServletRequest httpRequest) {
        tradeService.requestTrade(request, httpRequest);
        return new BaseResponse(ResponseCode.SUCCESS);
    }

    @PostMapping("/approve")
    public BaseResponse approveTrade(@RequestBody @Valid TradeRequest request,BindingResult result,  HttpServletRequest httpRequest) {
        tradeService.approveTrade(request, httpRequest);
        return new BaseResponse(ResponseCode.SUCCESS);
    }

    @GetMapping("/{product_no}")
    public BaseResponse getTrades(@PathVariable("product_no") int productNo, HttpServletRequest httpRequest) {
        return new ListResponse(ResponseCode.SUCCESS, tradeService.getTrades(productNo, httpRequest));
    }

    @GetMapping("/purchased/{page}")
    public BaseResponse getPurchasedTrades(@PathVariable("page") int page, HttpServletRequest httpRequest) {

        if (page < 0) {
            return new BaseResponse(ResponseCode.BAD_REQUEST);
        }

        return new ListResponse(ResponseCode.SUCCESS, tradeService.getPurchasedTrades(page - 1, httpRequest));
    }

    @GetMapping("/reserved/{page}")
    public BaseResponse getReservedTrades(@PathVariable("page") int page, HttpServletRequest httpRequest) {

        if (page < 0) {
            return new BaseResponse(ResponseCode.BAD_REQUEST);
        }

        return new ListResponse(ResponseCode.SUCCESS, tradeService.getReservedTrades(page - 1, httpRequest));
    }

    @GetMapping("/detail/{product_no}")
    public BaseResponse getTrade(@PathVariable("product_no") long productNo, HttpServletRequest httpRequest) {

        if(productNo < 0) {
         return new BaseResponse(ResponseCode.BAD_REQUEST);
        }

        return new DetailResponse(ResponseCode.SUCCESS, tradeService.getTrade(productNo, httpRequest));
    }
}
