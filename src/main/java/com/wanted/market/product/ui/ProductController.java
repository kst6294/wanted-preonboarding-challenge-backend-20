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
import com.wanted.market.product.ui.dto.request.QueryMineRequest;
import com.wanted.market.product.ui.dto.request.QueryRequest;
import com.wanted.market.product.ui.dto.request.RegisterRequest;
import com.wanted.market.product.ui.dto.response.DetailProductInfoResponse;
import com.wanted.market.product.ui.dto.response.SimpleProductInfoResponse;
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
    public ResponseEntity<?> getProducts(@LoginMember(required = false) SessionLoginMember loginMember, QueryRequest request) {
        ProductInfosResponse<SimpleProductInfoResponse> result = queryProductService.findAll(request);
        return ResponseEntity.ok(new ApiResponse<>("success", result));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProduct(@LoginMember(required = false) SessionLoginMember loginMember, @PathVariable Long id) {
        DetailProductInfoResponse result;
        try {
            result = queryProductService.findById(id, loginMember.getId());
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ApiResponse<>("success", result));
    }

    /**
     * <p>
     * <b>구매자에게는 구매 당시의 가격정보가, 판매자에게는 현재의 가격정보가 보여져야 한다.</b>
     * 판매자 입장에서는 해당 상품에 대한 주문을 특정할 수 없기 때문이다. (판매자는 다수의 구매자로부터 주문을 받는다.)
     * - (요구사항 13번)
     * </p>
     * <p>
     * 또한 <b>구매자 입장에서는 "주문의 상태"에 따른 상품을 볼 수 있어야 하고, 판매자 입장에서는 "상품의 상태"에 따른 상품을 볼 수 있어야 한다.</b>
     * 구매자의 관심사는 (매진여부같은) 상품의 상태가 아니라 "내 주문"의 상태이다.
     * 따라서 구매자에게 있어서 "구매한 용품"(또는 "예약중인 용품")은 (상품의 현재 상태가 아니라) "내 주문"의 상태에 따른 데이터들을 보여주어야 한다.
     * 반면에 판매자 입장에서는 해당 상품에 대한 주문을 특정할 수 없기 때문에 (특정 주문정보가 아니라) "상품"상태에 따른 데이터를 보여주는 것이 바람직하다.
     * 다만 판매자에게 있어서 "예약중인 용품"이 1.상품 그 자체의 상태가 예약중인지를 묻는 것인지, 2.(판매승인을 대기하고 있는) 구매요청이 있는 상품인지를 묻는 것인지 명확하지 않다.
     * 따라서 QueryRequest에 "status"(상품 상태)와 "hasOrder"(구매요청여부)를 파라미터로 받기로 한다.
     * - (요구사항 7번)
     * </p>
     * <p>
     * 요약하면
     * 1.구매자의 상품정보는 Order, 판매자의 상품정보는 Product에서 처리한다.
     * 2.판매자는 예약 중인 상태의 상품을 조회하고자 하면 "status"를, 아직 승인되지 않은 구매요청이 있는 상품을 조회하고자 하면 "hasOrder"를 파라미터로 담아 요청한다.
     * </p>
     */
    @GetMapping("/products/my")
    public ResponseEntity<?> getMyProducts(@LoginMember SessionLoginMember loginMember, QueryMineRequest request) {
        request.setSellerId(loginMember.getId());
        ProductInfosResponse<DetailProductInfoResponse> result = queryProductService.findAllWithOrders(request);
        return ResponseEntity.ok(new ApiResponse<>("success", result));
    }

    @PostMapping("/products")
    public ResponseEntity<?> postProduct(@LoginMember SessionLoginMember loginMember, @RequestBody RegisterRequest request) {
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
    public ResponseEntity<?> postOrder(@LoginMember SessionLoginMember loginMember, @PathVariable Long id, @RequestBody PlaceOrderRequest request) {
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
