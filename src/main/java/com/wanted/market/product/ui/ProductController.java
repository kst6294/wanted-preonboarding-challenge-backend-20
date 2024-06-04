package com.wanted.market.product.ui;

import com.wanted.market.common.authentication.model.SessionLoginMember;
import com.wanted.market.common.authentication.model.annotation.LoginMember;
import com.wanted.market.common.exception.InvalidRequestException;
import com.wanted.market.common.exception.NotFoundException;
import com.wanted.market.common.http.dto.response.ApiResponse;
import com.wanted.market.product.exception.OutDatedProductVersionException;
import com.wanted.market.product.service.PlaceOrderService;
import com.wanted.market.product.service.QueryProductService;
import com.wanted.market.product.service.RegisterProductService;
import com.wanted.market.product.ui.dto.request.PlaceOrderRequest;
import com.wanted.market.product.ui.dto.request.QueryRequest;
import com.wanted.market.product.ui.dto.request.RegisterRequest;
import com.wanted.market.product.ui.dto.response.ProductInfoResponse;
import com.wanted.market.product.ui.dto.response.ProductInfosResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class ProductController {
    private final QueryProductService queryProductService;
    private final RegisterProductService registerProductService;
    private final PlaceOrderService placeOrderService;

    public ProductController(QueryProductService queryProductService, RegisterProductService registerProductService, PlaceOrderService placeOrderService) {
        this.queryProductService = queryProductService;
        this.registerProductService = registerProductService;
        this.placeOrderService = placeOrderService;
    }

    @GetMapping("/products")
    public ResponseEntity getProducts(@LoginMember(required = false) SessionLoginMember loginMember, QueryRequest request) {
        ProductInfosResponse result = queryProductService.findAll(request);
        return ResponseEntity.ok(new ApiResponse<>("success", result));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity getProduct(@LoginMember(required = false) SessionLoginMember loginMember, @PathVariable Long id) {
        ProductInfoResponse result;
        try {
            result = queryProductService.findById(id);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ApiResponse<>("success", result));
    }

    /**
     * <p>
     * 구매자 입장에서는 "주문의 상태"에 따른 상품을 볼 수 있어야 하고, 판매자 입장에서는 "상품의 상태"에 따른 상품을 볼 수 있어야 한다.
     * 따라서 구매자의 상품정보는 Order에서, 판매자의 상품정보는 Product에서 처리하자
     * </p>
     */
    @GetMapping("/products/my")
    public ResponseEntity getMyProducts(@LoginMember SessionLoginMember loginMember, QueryRequest request) {
        request.setSellerId(loginMember.getId());
        ProductInfosResponse result = queryProductService.findAll(request);
        return ResponseEntity.ok(new ApiResponse<>("success", result));
    }

    @PostMapping("/products")
    public ResponseEntity postProduct(@LoginMember SessionLoginMember loginMember, @RequestBody RegisterRequest request) {
        Long newProductId;
        try {
            RegisterProductService.Command cmd =
                    new RegisterProductService.Command(loginMember.getId(), request.name(), request.price(), request.quantity());
            newProductId = registerProductService.register(cmd);
        } catch (InvalidRequestException e) {
            return ResponseEntity.badRequest()
                    .body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
        return ResponseEntity.created(URI.create("/products/" + newProductId))
                .body(new ApiResponse<>("success", null));
    }

    @PostMapping("/products/{id}/orders")
    public ResponseEntity postOrder(@LoginMember SessionLoginMember loginMember, @PathVariable Long id, @RequestBody PlaceOrderRequest request) {
        Long newOrderId;
        try {
            PlaceOrderService.Command cmd = new PlaceOrderService.Command(id, request.version(), loginMember.getId());
            newOrderId = placeOrderService.order(cmd);
        } catch (NotFoundException | InvalidRequestException e) {
            return ResponseEntity.badRequest()
                    .body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage()));
        } catch (OutDatedProductVersionException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage()));
        }
        return ResponseEntity.created(URI.create("/orders/" + newOrderId))
                .body(new ApiResponse<>("success", null));
    }
}
