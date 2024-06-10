package com.wanted.market.order.ui;

import com.wanted.market.common.authentication.model.SessionLoginMember;
import com.wanted.market.common.authentication.model.annotation.LoginMember;
import com.wanted.market.common.exception.InvalidRequestException;
import com.wanted.market.common.exception.NotFoundException;
import com.wanted.market.common.exception.UnauthorizedRequestException;
import com.wanted.market.common.http.dto.response.ApiResponse;
import com.wanted.market.order.exception.OutOfStockException;
import com.wanted.market.order.service.ConfirmOrderService;
import com.wanted.market.order.service.FinishOrderService;
import com.wanted.market.order.service.QueryOrderService;
import com.wanted.market.order.ui.dto.request.QueryRequest;
import com.wanted.market.order.ui.dto.response.OrderInfosResponse;
import com.wanted.market.order.ui.dto.response.SimpleOrderInfoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    private final QueryOrderService queryOrderService;
    private final ConfirmOrderService confirmOrderService;
    private final FinishOrderService finishOrderService;

    public OrderController(QueryOrderService queryOrderService, ConfirmOrderService confirmOrderService, FinishOrderService finishOrderService) {
        this.queryOrderService = queryOrderService;
        this.confirmOrderService = confirmOrderService;
        this.finishOrderService = finishOrderService;
    }

    @GetMapping("/orders/my")
    public ResponseEntity<?> getMyOrders(@LoginMember SessionLoginMember loginMember, QueryRequest request) {
        request.setBuyerId(loginMember.getId());
        OrderInfosResponse<SimpleOrderInfoResponse> result = queryOrderService.findAll(request);
        return ResponseEntity.ok(new ApiResponse("success", result));
    }

    @PostMapping("/orders/{id}/confirm")
    public ResponseEntity<?> confirmOrder(@LoginMember SessionLoginMember loginMember, @PathVariable Long id) throws UnauthorizedRequestException {
        try {
            ConfirmOrderService.Command cmd = new ConfirmOrderService.Command(id, loginMember.getId());
            confirmOrderService.confirm(cmd);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UnauthorizedRequestException e) {
            throw e;
        } catch (OutOfStockException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage()));
        } catch (InvalidRequestException e) {
            return ResponseEntity.badRequest().body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
        return ResponseEntity.ok(new ApiResponse<>("success", null));
    }

    @PostMapping("/orders/{id}/finish")
    public ResponseEntity<?> finishOrder(@LoginMember SessionLoginMember loginMember, @PathVariable Long id) throws UnauthorizedRequestException {
        try {
            FinishOrderService.Command cmd = new FinishOrderService.Command(id, loginMember.getId());
            finishOrderService.finish(cmd);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UnauthorizedRequestException e) {
            throw e;
        } catch (InvalidRequestException e) {
            return ResponseEntity.badRequest().body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
        return ResponseEntity.ok(new ApiResponse<>("success", null));
    }
}
