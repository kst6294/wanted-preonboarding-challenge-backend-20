package kr.co.wanted.market.trade.controller;

import kr.co.wanted.market.common.global.dto.ApiResult;
import kr.co.wanted.market.common.global.dto.PageInfo;
import kr.co.wanted.market.common.global.dto.PageResult;
import kr.co.wanted.market.trade.dto.Purchase;
import kr.co.wanted.market.trade.dto.TradeHistory;
import kr.co.wanted.market.trade.dto.TradeStateModification;
import kr.co.wanted.market.trade.service.TradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/trades")
public class TradeController implements TradeControllerDocs {

    private final TradeService tradeService;


    @Override
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResult<Purchase>> purchase(@RequestBody Purchase purchase) {

        Purchase purchaseResult = tradeService.purchase(purchase);

        return ResponseEntity.ok(new ApiResult<>(purchaseResult));
    }

    @Override
    @GetMapping("/sales")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResult<PageResult<TradeHistory>>> searchSaleTrades(@ModelAttribute PageInfo pageInfo) {

        PageResult<TradeHistory> sales = tradeService.searchSales(pageInfo);

        return ResponseEntity.ok(new ApiResult<>(sales));
    }


    @Override
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResult<TradeStateModification>> changeTradeState(@PathVariable("id") Long id,
                                                                              @RequestBody TradeStateModification tradeStateModification) {

        TradeStateModification changedState = tradeService.changeState(id, tradeStateModification.state());

        return ResponseEntity.ok(new ApiResult<>(changedState));
    }


    @Override
    @GetMapping("/purchases")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResult<PageResult<TradeHistory>>> searchPurchaseTrades(@ModelAttribute PageInfo pageInfo) {

        PageResult<TradeHistory> purchases = tradeService.searchPurchases(pageInfo);

        return ResponseEntity.ok(new ApiResult<>(purchases));
    }

}
