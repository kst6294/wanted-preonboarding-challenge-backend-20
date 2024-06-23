package kr.co.wanted.market.trade.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.wanted.market.common.global.dto.ApiResult;
import kr.co.wanted.market.common.global.dto.PageInfo;
import kr.co.wanted.market.common.global.dto.PageResult;
import kr.co.wanted.market.trade.dto.Purchase;
import kr.co.wanted.market.trade.dto.TradeHistory;
import kr.co.wanted.market.trade.dto.TradeStateModification;
import org.springframework.http.ResponseEntity;

@Tag(name = "거래 API", description = "거래 관련 API")
public interface TradeControllerDocs {

    @Operation(
            summary = "거래시작",
            description = "상품을 구매합니다. 회원만 가능하며, 거래가 시작됩니다.",
            requestBody = @RequestBody(content = @Content(
                    examples = {
                            @ExampleObject(name = "예시", value = """
                                    {
                                        "productId": 1
                                    }
                                    """)
                    }
            ))
    )
    ResponseEntity<ApiResult<Purchase>> purchase(Purchase purchase);


    @Operation(
            summary = "판매거래 조회",
            description = """
                    로그인 회원의 판매거래를 조회합니다.
                    
                    페이징 정보가 필요합니다.
                    
                    page: 페이지 번호 (기본값 0)
                    
                    size: 페이지당 사이즈 (기본값 5)
                    """
    )
    ResponseEntity<ApiResult<PageResult<TradeHistory>>> searchSaleTrades(PageInfo pageInfo);


    @Operation(
            summary = "거래상태 변경",
            description = """
                    거래 상태를 변경합니다. 취소한다면 상태가 한단계 되돌아갑니다.
                    
                    판매자는 ['BOOKING' / 'CANCEL'(이전 상태가 '예약중' 일때)],
                    
                    구매자는 ['CONFIRMATION' / 'CANCEL(이전 상태가 '거래요청' 일때)'] 가능합니다.
                    
                    거래상태 순서: REQUEST -> BOOKING -> CONFIRMATION
                    """,
            requestBody = @RequestBody(content = @Content(
                    examples = {
                            @ExampleObject(name = "예시", value = """
                                    {
                                        "state": "BOOKING"
                                    }
                                    """)
                    }
            ))
    )
    ResponseEntity<ApiResult<TradeStateModification>> changeTradeState(Long id,
                                                                       TradeStateModification tradeStateModification);


    @Operation(
            summary = "구매거래 조회",
            description = """
                    로그인 회원의 구매거래를 조회합니다.
                    
                    페이징 정보가 필요합니다.
                    
                    page: 페이지 번호 (기본값 0)
                    
                    size: 페이지당 사이즈 (기본값 5)
                    """
    )
    ResponseEntity<ApiResult<PageResult<TradeHistory>>> searchPurchaseTrades(PageInfo pageInfo);

}
