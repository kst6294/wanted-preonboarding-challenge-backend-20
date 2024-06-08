package market.market.domain.transaction.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import market.market.domain.transaction.presentation.dto.response.QueryTransactionDetailResponse;
import market.market.domain.transaction.presentation.dto.response.QueryTransactionListResponse;
import market.market.domain.transaction.service.CreateTransactionService;
import market.market.domain.transaction.service.QueryProductTransactionDetailService;
import market.market.domain.transaction.service.QueryTransactionListService;
import market.market.domain.transaction.service.QueryTransactionReListService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
@Tag(name = "Transaction API", description = "Transaction에 대한 API입니다.")
public class TransactionController {
    private final CreateTransactionService createTransactionService;
    private final QueryTransactionListService queryTransactionListService;
    private final QueryTransactionReListService queryTransactionReListService;
    private final QueryProductTransactionDetailService queryProductTransactionDetailService;

    @Operation(summary = "거래 등록", description = "거래를 등록합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create/{product_id}")
    public void create(@PathVariable(name = "product_id") Long id) {
        createTransactionService.execute(id);
    }

    @Operation(summary = "구매 완료 리스트", description = "자신의 구매한 용품을 볼 수 있습니다.")
    @GetMapping("/list/completion")
    public List<QueryTransactionListResponse> listCompletion() {
        return queryTransactionListService.execute();
    }


    @Operation(summary = "예약 리스트", description = "(판매자/구매자) 예약 용품을 볼 수 있습니다.")
    @GetMapping("/list/reservation")
    public List<QueryTransactionListResponse> listReservation() {
        return queryTransactionReListService.execute();
    }

    @Operation(summary = "거래내역 조회", description = "용품에 대한 (판매자/구매자)라면 거래내역을 볼 수 있습니다.")
    @GetMapping("/detail/{product_id}")
    public QueryTransactionDetailResponse detail(@PathVariable(name = "product_id") Long id) {
        return queryProductTransactionDetailService.execute(id);
    }

}
