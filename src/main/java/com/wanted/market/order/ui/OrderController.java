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
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    private final ConfirmOrderService confirmOrderService;
    private final FinishOrderService finishOrderService;

    public OrderController(ConfirmOrderService confirmOrderService, FinishOrderService finishOrderService) {
        this.confirmOrderService = confirmOrderService;
        this.finishOrderService = finishOrderService;
    }

    @PostMapping("/orders/{id}/confirm")
    public ResponseEntity confirmOrder(@LoginMember SessionLoginMember loginMember, @PathVariable Long id) throws UnauthorizedRequestException {
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
    public ResponseEntity finishOrder(@LoginMember SessionLoginMember loginMember, @PathVariable Long id) throws UnauthorizedRequestException {
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
