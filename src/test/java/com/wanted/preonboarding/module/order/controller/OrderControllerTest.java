package com.wanted.preonboarding.module.order.controller;

import com.wanted.preonboarding.auth.config.AuthConstants;
import com.wanted.preonboarding.data.auth.AuthModuleHelper;
import com.wanted.preonboarding.data.order.OrderModuleHelper;
import com.wanted.preonboarding.data.product.ProductFactory;
import com.wanted.preonboarding.data.product.ProductModuleHelper;
import com.wanted.preonboarding.data.users.UsersModuleHelper;
import com.wanted.preonboarding.document.utils.DocumentLinkGenerator;
import com.wanted.preonboarding.document.utils.RestDocsTestSupport;
import com.wanted.preonboarding.module.exception.order.InvalidUpdateOrderStatusException;
import com.wanted.preonboarding.module.exception.product.NotFoundProductException;
import com.wanted.preonboarding.module.order.core.BaseOrderContext;
import com.wanted.preonboarding.module.order.dto.CreateOrder;
import com.wanted.preonboarding.module.order.dto.UpdateOrder;
import com.wanted.preonboarding.module.order.entity.Order;
import com.wanted.preonboarding.module.order.enums.OrderStatus;
import com.wanted.preonboarding.module.order.service.OrderUpdateService;
import com.wanted.preonboarding.module.product.dto.CreateProduct;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.user.core.BaseUserInfo;
import com.wanted.preonboarding.module.user.entity.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest extends RestDocsTestSupport {

    @MockBean
    private OrderUpdateService orderUpdateService;

    @Test
    @DisplayName("주문 생성")
    void createOrder_Success() throws Exception {

        Users buyer = UsersModuleHelper.toUsersWithId();
        BaseUserInfo userInfo = AuthModuleHelper.toBaseUserInfo(buyer);
        when(userFindService.fetchUserInfo(anyString())).thenReturn(userInfo);
        securityUserMockSetting(buyer);

        //given
        CreateProduct createProduct = ProductModuleHelper.toCreateProductWithUsers();
        Product product = ProductFactory.generateProduct(createProduct);

        CreateOrder createOrder = OrderModuleHelper.toCreateOrder(product);
        Order order = OrderModuleHelper.toOrderWithId(product, buyer);

        BaseOrderContext baseOrderContext = OrderModuleHelper.toBaseOrderContext(order, buyer.getEmail());

        when(orderQueryService.doOrder(any(CreateOrder.class))).thenReturn(baseOrderContext);
        when(productFindService.fetchProductEntity(anyLong())).thenReturn(product);
        when(orderLockChecker.lock(anyLong())).thenReturn(true);



        mockMvc.perform(post("/api/v1/order")
                        .header(AuthConstants.AUTHORIZATION_HEADER, AuthConstants.BEARER_PREFIX + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOrder)))
                .andExpect(status().isOk())
                .andDo(document("order/create-order",
                        requestFields(
                                fieldWithPath("productId").type(JsonFieldType.NUMBER).description("제품 ID")
                        ),
                        responseFields(
                                beneathPath("data"),
                                fieldWithPath("orderId").type(JsonFieldType.NUMBER).description("주문 ID"),
                                fieldWithPath("productId").type(JsonFieldType.NUMBER).description("제품 ID"),
                                fieldWithPath("seller").type(JsonFieldType.STRING).description("판매자 EMAIL"),
                                fieldWithPath("buyer").type(JsonFieldType.STRING).description("구매자 EMAIL"),
                                fieldWithPath("orderStatus").type(JsonFieldType.STRING).description(DocumentLinkGenerator.generateLinkCode(DocumentLinkGenerator.DocUrl.ORDER_STATUS))
                        ),
                        responseFields(
                                beneathPath("response"),
                                statusMsg()
                        )
                ));
    }

    @Test
    @DisplayName("주문 실패 - 비회원")
    void createOrder_unAuthorization() throws Exception {
        // given
        CreateProduct createProduct = ProductModuleHelper.toCreateProductWithUsers();
        Product product = ProductFactory.generateProduct(createProduct);

        CreateOrder createOrder = OrderModuleHelper.toCreateOrder(product);

        when(productFindService.fetchProductEntity(anyLong())).thenReturn(product);
        when(orderLockChecker.lock(anyLong())).thenReturn(true);


        // when & then
        mockMvc.perform(post("/api/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOrder)))
                .andExpect(status().isUnauthorized())
                .andDo(document("product/post-order-unauthorized",
                        requestFields(
                                fieldWithPath("productId").type(JsonFieldType.NUMBER).description("제품 ID")
                        )
                ));
    }



    @Test
    @DisplayName("유효하지 않은 주문 - 상품 없음")
    void createOrder_InvalidProduct() throws Exception {

        Users buyer = UsersModuleHelper.toUsersWithId();
        BaseUserInfo userInfo = AuthModuleHelper.toBaseUserInfo(buyer);
        when(userFindService.fetchUserInfo(anyString())).thenReturn(userInfo);
        securityUserMockSetting(buyer);

        //given
        CreateOrder createOrder = OrderModuleHelper.toCreateOrder();
        when(productFindService.fetchProductEntity(anyLong())).thenThrow(new NotFoundProductException(createOrder.getProductId()));

        mockMvc.perform(post("/api/v1/order")
                        .header(AuthConstants.AUTHORIZATION_HEADER, AuthConstants.BEARER_PREFIX + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOrder)))
                .andExpect(status().isBadRequest())
                .andDo(document("order/create-order-invalid-product",
                        requestFields(
                                fieldWithPath("productId").type(JsonFieldType.NUMBER).description("제품 ID")
                        ),
                        responseFields(
                                errorStatusMsg()
                        )
                ));
    }


    @Test
    @DisplayName("유효하지 않은 주문 - 이미 예약 중 또는 판매 완료된 제품")
    void createOrder_OutOfStock() throws Exception {

        Users buyer = UsersModuleHelper.toUsersWithId();
        BaseUserInfo userInfo = AuthModuleHelper.toBaseUserInfo(buyer);
        when(userFindService.fetchUserInfo(anyString())).thenReturn(userInfo);
        securityUserMockSetting(buyer);

        //given
        CreateProduct createProduct = ProductModuleHelper.toCreateProductWithUsers();
        Product product = ProductFactory.generateProduct(createProduct);
        product.doBooking();
        CreateOrder createOrder = OrderModuleHelper.toCreateOrder(product);
        Order order = OrderModuleHelper.toOrderWithId(product, buyer);

        BaseOrderContext baseOrderContext = OrderModuleHelper.toBaseOrderContext(order, buyer.getEmail());

        when(orderQueryService.doOrder(any(CreateOrder.class))).thenReturn(baseOrderContext);
        when(productFindService.fetchProductEntity(anyLong())).thenReturn(product);
        when(orderLockChecker.lock(anyLong())).thenReturn(true);


        mockMvc.perform(post("/api/v1/order")
                        .header(AuthConstants.AUTHORIZATION_HEADER, AuthConstants.BEARER_PREFIX + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOrder)))
                .andExpect(status().isBadRequest())
                .andDo(document("order/create-order-out-of-stock",
                        requestFields(
                                fieldWithPath("productId").type(JsonFieldType.NUMBER).description("제품 ID")
                        ),
                        responseFields(
                                errorStatusMsg()
                        )
                ));
    }


    @Test
    @DisplayName("주문 수정 - COMPLETED")
    void updateOrderCompleted_Success() throws Exception {

        Users buyer = UsersModuleHelper.toUsersWithId();
        BaseUserInfo userInfo = AuthModuleHelper.toBaseUserInfo(buyer);
        when(userFindService.fetchUserInfo(anyString())).thenReturn(userInfo);
        securityUserMockSetting(buyer);

        //given
        CreateProduct createProduct = ProductModuleHelper.toCreateProductWithUsers();
        Product product = ProductFactory.generateProduct(createProduct);
        Order order = OrderModuleHelper.toOrderWithId(product, buyer);
        UpdateOrder updateOrder = OrderModuleHelper.toUpdateOrder(order, OrderStatus.COMPLETED);

        BaseOrderContext baseOrderContext = OrderModuleHelper.toBaseOrderContext(order, buyer.getEmail());

        when(orderUpdateServiceProvider.get(updateOrder.getOrderStatus())).thenReturn(orderUpdateService);
        when(orderUpdateService.updateOrder(any(UpdateOrder.class))).thenReturn(baseOrderContext);
        when(orderFindService.fetchOrderEntity(anyLong())).thenReturn(order);
        when(orderStatusTransitionProvider.get(any(OrderStatus.class))).thenReturn(orderStatusTransition);
        when(orderStatusTransition.canTransition(any(OrderStatus.class), any(Order.class), anyString())).thenReturn(true);

        mockMvc.perform(patch("/api/v1/order")
                        .header(AuthConstants.AUTHORIZATION_HEADER, AuthConstants.BEARER_PREFIX + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateOrder)))
                .andExpect(status().isOk())
                .andDo(document("order/update-order-completed",
                        requestFields(
                                fieldWithPath("orderId").type(JsonFieldType.NUMBER).description("주문 ID"),
                                fieldWithPath("orderStatus").type(JsonFieldType.STRING).description("주문 상태")
                        ),
                        responseFields(
                                beneathPath("data"),
                                fieldWithPath("orderId").type(JsonFieldType.NUMBER).description("주문 ID (PK)"),
                                fieldWithPath("productId").type(JsonFieldType.NUMBER).description("제품 ID"),
                                fieldWithPath("seller").type(JsonFieldType.STRING).description("판매자 EMAIL"),
                                fieldWithPath("buyer").type(JsonFieldType.STRING).description("구매자 EMAIL"),
                                fieldWithPath("orderStatus").type(JsonFieldType.STRING).description(DocumentLinkGenerator.generateLinkCode(DocumentLinkGenerator.DocUrl.ORDER_STATUS))
                        ),
                        responseFields(
                                beneathPath("response"),
                                statusMsg()
                        )
                ));
    }


    @Test
    @DisplayName("주문 수정 - ORDERED 에서 SETTLEMENT 로 잘못된 요청 ")
    void invalid_updateOrderCompleted_Success() throws Exception {

        Users buyer = UsersModuleHelper.toUsersWithId();
        BaseUserInfo userInfo = AuthModuleHelper.toBaseUserInfo(buyer);
        when(userFindService.fetchUserInfo(anyString())).thenReturn(userInfo);
        securityUserMockSetting(buyer);

        //given
        CreateProduct createProduct = ProductModuleHelper.toCreateProductWithUsers();
        Product product = ProductFactory.generateProduct(createProduct);
        Order order = OrderModuleHelper.toOrderWithId(product, buyer);
        UpdateOrder updateOrder = OrderModuleHelper.toUpdateOrder(order, OrderStatus.SETTLEMENT);

        BaseOrderContext baseOrderContext = OrderModuleHelper.toBaseOrderContext(order, buyer.getEmail());

        when(orderUpdateServiceProvider.get(updateOrder.getOrderStatus())).thenReturn(orderUpdateService);
        when(orderUpdateService.updateOrder(any(UpdateOrder.class))).thenReturn(baseOrderContext);
        when(orderFindService.fetchOrderEntity(anyLong())).thenReturn(order);
        when(orderStatusTransitionProvider.get(any(OrderStatus.class))).thenReturn(orderStatusTransition);
        when(orderStatusTransition.canTransition(any(OrderStatus.class), any(Order.class), anyString()))
                .thenThrow(new InvalidUpdateOrderStatusException(order.getId(), order.getOrderStatus(), updateOrder.getOrderStatus()));

        mockMvc.perform(patch("/api/v1/order")
                        .header(AuthConstants.AUTHORIZATION_HEADER, AuthConstants.BEARER_PREFIX + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateOrder)))
                .andExpect(status().isBadRequest())
                .andDo(document("order/update-order-completed-invalid-status",
                        requestFields(
                                fieldWithPath("orderId").type(JsonFieldType.NUMBER).description("주문 ID"),
                                fieldWithPath("orderStatus").type(JsonFieldType.STRING).description("주문 상태")
                        ),
                        responseFields(
                                errorStatusMsg()
                        )
                ));
    }


    @Test
    @DisplayName("주문 수정 - SETTLEMENT")
    void updateOrderSettlement_Success() throws Exception {

        Users buyer = UsersModuleHelper.toUsersWithId();
        BaseUserInfo userInfo = AuthModuleHelper.toBaseUserInfo(buyer);
        when(userFindService.fetchUserInfo(anyString())).thenReturn(userInfo);
        securityUserMockSetting(buyer);

        //given
        CreateProduct createProduct = ProductModuleHelper.toCreateProductWithUsers();
        Product product = ProductFactory.generateProduct(createProduct);
        Order order = OrderModuleHelper.toOrderWithId(product, buyer);
        order.changeOrderStatus(OrderStatus.COMPLETED);
        UpdateOrder updateOrder = OrderModuleHelper.toUpdateOrder(order, OrderStatus.SETTLEMENT);

        BaseOrderContext baseOrderContext = OrderModuleHelper.toBaseOrderContext(order, buyer.getEmail());

        when(orderUpdateServiceProvider.get(updateOrder.getOrderStatus())).thenReturn(orderUpdateService);
        when(orderUpdateService.updateOrder(any(UpdateOrder.class))).thenReturn(baseOrderContext);
        when(orderFindService.fetchOrderEntity(anyLong())).thenReturn(order);
        when(orderStatusTransitionProvider.get(any(OrderStatus.class))).thenReturn(orderStatusTransition);
        when(orderStatusTransition.canTransition(any(OrderStatus.class), any(Order.class), anyString())).thenReturn(true);

        mockMvc.perform(patch("/api/v1/order")
                        .header(AuthConstants.AUTHORIZATION_HEADER, AuthConstants.BEARER_PREFIX + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateOrder)))
                .andExpect(status().isOk())
                .andDo(document("order/update-order-settlement",
                        requestFields(
                                fieldWithPath("orderId").type(JsonFieldType.NUMBER).description("주문 ID"),
                                fieldWithPath("orderStatus").type(JsonFieldType.STRING).description("주문 상태")
                        ),
                        responseFields(
                                beneathPath("data"),
                                fieldWithPath("orderId").type(JsonFieldType.NUMBER).description("주문 ID (PK)"),
                                fieldWithPath("productId").type(JsonFieldType.NUMBER).description("제품 ID"),
                                fieldWithPath("seller").type(JsonFieldType.STRING).description("판매자 EMAIL"),
                                fieldWithPath("buyer").type(JsonFieldType.STRING).description("구매자 EMAIL"),
                                fieldWithPath("orderStatus").type(JsonFieldType.STRING).description(DocumentLinkGenerator.generateLinkCode(DocumentLinkGenerator.DocUrl.ORDER_STATUS))
                        ),
                        responseFields(
                                beneathPath("response"),
                                statusMsg()
                        )
                ));
    }


    @Test
    @DisplayName("주문 수정 - COMPLETED 에서 COMPLETED 로 잘못된 요청 ")
    void invalid_updateSettlement_Success() throws Exception {

        Users buyer = UsersModuleHelper.toUsersWithId();
        BaseUserInfo userInfo = AuthModuleHelper.toBaseUserInfo(buyer);
        when(userFindService.fetchUserInfo(anyString())).thenReturn(userInfo);
        securityUserMockSetting(buyer);

        //given
        CreateProduct createProduct = ProductModuleHelper.toCreateProductWithUsers();
        Product product = ProductFactory.generateProduct(createProduct);
        Order order = OrderModuleHelper.toOrderWithId(product, buyer);
        order.changeOrderStatus(OrderStatus.COMPLETED);
        UpdateOrder updateOrder = OrderModuleHelper.toUpdateOrder(order, OrderStatus.COMPLETED);

        BaseOrderContext baseOrderContext = OrderModuleHelper.toBaseOrderContext(order, buyer.getEmail());

        when(orderUpdateServiceProvider.get(updateOrder.getOrderStatus())).thenReturn(orderUpdateService);
        when(orderUpdateService.updateOrder(any(UpdateOrder.class))).thenReturn(baseOrderContext);
        when(orderFindService.fetchOrderEntity(anyLong())).thenReturn(order);
        when(orderStatusTransitionProvider.get(any(OrderStatus.class))).thenReturn(orderStatusTransition);
        when(orderStatusTransition.canTransition(any(OrderStatus.class), any(Order.class), anyString()))
                .thenThrow(new InvalidUpdateOrderStatusException(order.getId(), order.getOrderStatus(), updateOrder.getOrderStatus()));

        mockMvc.perform(patch("/api/v1/order")
                        .header(AuthConstants.AUTHORIZATION_HEADER, AuthConstants.BEARER_PREFIX + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateOrder)))
                .andExpect(status().isBadRequest())
                .andDo(document("order/update-order-settlement-invalid-status",
                        requestFields(
                                fieldWithPath("orderId").type(JsonFieldType.NUMBER).description("주문 ID"),
                                fieldWithPath("orderStatus").type(JsonFieldType.STRING).description("주문 상태")
                        ),
                        responseFields(
                                errorStatusMsg()
                        )
                ));
    }




}