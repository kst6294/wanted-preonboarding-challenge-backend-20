package wanted.market.domain.transcation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import wanted.market.domain.transcation.controller.dto.TransactionCancelRequest;
import wanted.market.domain.transcation.controller.dto.TransactionCreateRequest;
import wanted.market.domain.transcation.service.TransactionService;
import wanted.market.domain.transcation.service.dto.response.TransactionCreateResponse;
import wanted.market.domain.transcation.service.dto.response.TransactionListResponse;
import wanted.market.global.dto.ApiResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/create")
    public ApiResponse<TransactionCreateResponse> createTransaction(@RequestBody TransactionCreateRequest request,
                                                                    @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.ok(transactionService.createTransaction(request.toService(userDetails.getUsername())));
    }

    @GetMapping("/list")
    public ApiResponse<List<TransactionListResponse>> createTransaction(@RequestParam("email") String email) {
        return ApiResponse.ok(transactionService.findTransactionWithMember(email));
    }

    @PostMapping("/cancel")
    public ApiResponse<Boolean> cancelTransaction(@RequestBody TransactionCancelRequest request,
                                                                        @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.ok(transactionService.cancelTransaction(request.toService(userDetails.getUsername())));
    }

    @GetMapping("/complete")
    public ApiResponse<Boolean> completeTransaction(@RequestParam("transactionId") Long transactionId) {
        return ApiResponse.ok(transactionService.completeTransaction(transactionId));
    }
}
