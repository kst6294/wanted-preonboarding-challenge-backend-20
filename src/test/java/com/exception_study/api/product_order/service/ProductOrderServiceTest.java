package com.exception_study.api.product_order.service;

import com.exception_study.api.product.entity.Product;
import com.exception_study.api.product.repository.ProductRepository;
import com.exception_study.api.product_order.entity.ProductOrder;
import com.exception_study.api.product_order.repository.ProductOrderRepository;
import com.exception_study.api.user_account.entity.UserAccount;
import com.exception_study.api.user_account.repository.UserAccountRepository;
import com.exception_study.global.exception.ErrorCode;
import com.exception_study.global.exception.StudyApplicationException;
import com.exception_study.global.fixture.product.ProductFixture;
import com.exception_study.global.fixture.product.ProductInfoFixture;
import com.exception_study.global.fixture.product_order.ProductOrderFixture;
import com.exception_study.global.fixture.product_order.ProductOrderInfoFixture;
import com.exception_study.global.fixture.user.UserAccountFixture;
import com.exception_study.global.fixture.user.UserInfoFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("비즈니스 로직 - 주문, 예약, 구매확정, 구매내역 조회")
@SpringBootTest
@ActiveProfiles("test")
public class ProductOrderServiceTest {
    @Autowired
    ProductOrderService productOrderService;

    @MockBean
    ProductOrderRepository productOrderRepository;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    UserAccountRepository userAccountRepository;

    /**
     * 기본적인 예외상황(없는 유저, 없는 상품, 예약된 상품, 품절인 상품) 등에 대한 테스트는 예약 로직에서 일괄적으로 확인했음.
     * 따라서 다른 로직에 겹치는 예외 상황에 대한 테스트 로직은 작성하지 않고 진행함.
     */


    @DisplayName("상품을 예약하면 주문이 생성된다.")
    @Test
    public void givenAuthenticatedUser_whenReserving_thenSuccess() {
        //given
        UserInfoFixture.TestInfo userFixture = UserInfoFixture.get();
        ProductInfoFixture.TestInfo productFixture = ProductInfoFixture.get();
        ProductOrderInfoFixture.TestInfo productOrderFixture = ProductOrderInfoFixture.get();
        UserAccount testSeller = UserAccountFixture.get(userFixture.getUserId(), userFixture.getPassword(), userFixture.getUserName());
        UserAccount testBuyer = UserAccountFixture.get(userFixture.getUserId(), userFixture.getPassword(), userFixture.getUserName());
        Product testProduct = ProductFixture.get(productFixture.getId(),
                productFixture.getName(),
                productFixture.getPrice(),
                productFixture.getStatus(),
                productFixture.getQuantity(), testSeller
        );
        ProductOrder testProductOrder = ProductOrderFixture.get(
                productOrderFixture.getId(),
                testProduct,
                productOrderFixture.getPrice(),
                testSeller,
                testBuyer,
                productFixture.getStatus(),
                "예약중"
        );
        //when
        when(userAccountRepository.findById(userFixture.getUserId())).thenReturn(Optional.of(testBuyer));
        when(productRepository.findById(productFixture.getId())).thenReturn(Optional.of(testProduct));
        when(productOrderRepository.findByProduct_IdAndBuyer(productFixture.getId(), testBuyer)).thenReturn(null);
        when(productOrderRepository.save(any())).thenReturn(testProductOrder);

        //then
        assertDoesNotThrow(
                () -> productOrderService.reserve(productFixture.getId(), userFixture.getUserId())
        );
    }

    @DisplayName("없는 유저가 예약 요청할 경우 실패한다.")
    @Test
    public void givenNotExistsUser_whenReserving_thenException() {
        //given
        ProductInfoFixture.TestInfo productFixture = ProductInfoFixture.get();
        ProductOrderInfoFixture.TestInfo productOrderFixture = ProductOrderInfoFixture.get();
        UserInfoFixture.TestInfo userFixture = UserInfoFixture.get();
        //when
        StudyApplicationException exception = Assertions.assertThrows(StudyApplicationException.class,
                () -> productOrderService.reserve(productFixture.getId(), userFixture.getUserId())
        );
        //then
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

    @DisplayName("없는 상품을 예약 요청할 경우 실패한다.")
    @Test
    public void givenNotExistsProduct_whenReserving_thenException() {
        //given
        ProductInfoFixture.TestInfo productFixture = ProductInfoFixture.get();
        UserInfoFixture.TestInfo userFixture = UserInfoFixture.get();
        //when
        when(userAccountRepository.findById(userFixture.getUserId()))
                .thenReturn(Optional.of(
                        UserAccountFixture.get(
                                userFixture.getUserId(), userFixture.getPassword(), userFixture.getUserName()
                        )
                ));
        StudyApplicationException exception = Assertions.assertThrows(StudyApplicationException.class,
                () -> productOrderService.reserve(productFixture.getId(), userFixture.getUserId())
        );
        //then
        assertEquals(ErrorCode.PRODUCT_NOT_FOUND, exception.getErrorCode());
    }

    @DisplayName("이미 예약한 상품을 예약 요청할 경우 실패한다.")
    @Test
    public void givenAlreadyReservedProduct_whenReserving_thenException() {
        //given
        UserInfoFixture.TestInfo userFixture = UserInfoFixture.get();
        ProductInfoFixture.TestInfo productFixture = ProductInfoFixture.get();
        ProductOrderInfoFixture.TestInfo productOrderFixture = ProductOrderInfoFixture.get();

        UserAccount testSeller = UserAccountFixture.get(userFixture.getUserId(), userFixture.getPassword(), userFixture.getUserName());
        UserAccount testBuyer = UserAccountFixture.get(userFixture.getUserId(), userFixture.getPassword(), userFixture.getUserName());
        Product testProduct = ProductFixture.get(productFixture.getId(),
                productFixture.getName(),
                productFixture.getPrice(),
                productFixture.getStatus(),
                productFixture.getQuantity(), testSeller
        );
        ProductOrder testProductOrder = ProductOrderFixture.get(
                productOrderFixture.getId(),
                testProduct,
                productOrderFixture.getPrice(),
                testSeller,
                testBuyer,
                productFixture.getStatus(),
                "예약중"
        );
        //when
        when(userAccountRepository.findById(userFixture.getUserId())).thenReturn(Optional.of(testBuyer));
        when(productRepository.findById(productFixture.getId())).thenReturn(Optional.of(testProduct));
        when(productOrderRepository.findByProduct_IdAndBuyer(productFixture.getId(), testBuyer)).thenReturn(testProductOrder);

        StudyApplicationException exception = Assertions.assertThrows(StudyApplicationException.class,
                () -> productOrderService.reserve(productFixture.getId(), userFixture.getUserId())
        );
        //then
        assertEquals(ErrorCode.PRODUCT_ALREADY_RESERVED, exception.getErrorCode());
    }

    @DisplayName("품절된 상품을 예약 요청할 경우 실패한다.")
    @Test
    public void givenSoldOutProduct_whenReserving_thenException() {
        //given
        UserInfoFixture.TestInfo userFixture = UserInfoFixture.get();
        ProductInfoFixture.TestInfo productFixture = ProductInfoFixture.get();
        ProductOrderInfoFixture.TestInfo productOrderFixture = ProductOrderInfoFixture.get();

        UserAccount testSeller = UserAccountFixture.get(userFixture.getUserId(), userFixture.getPassword(), userFixture.getUserName());
        UserAccount testBuyer = UserAccountFixture.get(userFixture.getUserId(), userFixture.getPassword(), userFixture.getUserName());
        Product testProduct = ProductFixture.get(productFixture.getId(),
                productFixture.getName(),
                productFixture.getPrice(),
                productFixture.getStatus(),
                0, testSeller
        );
        ProductOrder testProductOrder = ProductOrderFixture.get(
                productOrderFixture.getId(),
                testProduct,
                productOrderFixture.getPrice(),
                testSeller,
                testBuyer,
                productFixture.getStatus(),
                "예약중"
        );

        //when
        when(userAccountRepository.findById(userFixture.getUserId())).thenReturn(Optional.of(testBuyer));
        when(productRepository.findById(productFixture.getId())).thenReturn(Optional.of(testProduct));
        when(productOrderRepository.findByProduct_IdAndBuyer(productFixture.getId(), testBuyer)).thenReturn(null);

        StudyApplicationException exception = Assertions.assertThrows(StudyApplicationException.class,
                () -> productOrderService.reserve(productFixture.getId(), userFixture.getUserId())
        );
        //then
        assertEquals(ErrorCode.PRODUCT_SOLD_OUT, exception.getErrorCode());
    }

    @DisplayName("예약 요청이 온 주문을 승인하면 성공한다.")
    @Test
    public void givenReservedProductOrder_whenApproving_thenSuccess() {
        //given
        UserInfoFixture.TestInfo userFixture = UserInfoFixture.get();
        ProductInfoFixture.TestInfo productFixture = ProductInfoFixture.get();
        ProductOrderInfoFixture.TestInfo productOrderFixture = ProductOrderInfoFixture.get();

        UserAccount testSeller = UserAccountFixture.get(userFixture.getUserId(), userFixture.getPassword(), userFixture.getUserName());
        UserAccount testBuyer = UserAccountFixture.get(userFixture.getUserId(), userFixture.getPassword(), userFixture.getUserName());
        Product testProduct = ProductFixture.get(productFixture.getId(),
                productFixture.getName(),
                productFixture.getPrice(),
                productFixture.getStatus(),
                productFixture.getQuantity(), testSeller
        );
        ProductOrder testProductOrder = ProductOrderFixture.get(
                productOrderFixture.getId(),
                testProduct,
                productOrderFixture.getPrice(),
                testSeller,
                testBuyer,
                productFixture.getStatus(),
                "예약중"
        );

        //when
        when(userAccountRepository.findById(userFixture.getUserId())).thenReturn(Optional.of(testBuyer));
        when(productOrderRepository.findById(productOrderFixture.getId())).thenReturn(Optional.of(testProductOrder));

        productOrderService.approve(productOrderFixture.getId(), testSeller.getUserId());

        //then

        assertEquals(2, testProduct.getQuantity());
        assertEquals("예약중", testProductOrder.getSellerStatus());
        assertEquals("예약중", testProductOrder.getBuyerStatus());
    }

    @DisplayName("예약 요청이 온 주문을 승인하면 성공한다. (마지막 주문)")
    @Test
    public void givenLastReservedProductOrder_whenApproving_thenSuccess() {
        //given
        UserInfoFixture.TestInfo userFixture = UserInfoFixture.get();
        ProductInfoFixture.TestInfo productFixture = ProductInfoFixture.get();
        ProductOrderInfoFixture.TestInfo productOrderFixture = ProductOrderInfoFixture.get();

        UserAccount testSeller = UserAccountFixture.get(userFixture.getUserId(), userFixture.getPassword(), userFixture.getUserName());
        UserAccount testBuyer = UserAccountFixture.get(userFixture.getUserId(), userFixture.getPassword(), userFixture.getUserName());
        Product testProduct = ProductFixture.get(productFixture.getId(),
                productFixture.getName(),
                productFixture.getPrice(),
                productFixture.getStatus(),
                1, testSeller
        );
        ProductOrder testProductOrder = ProductOrderFixture.get(
                productOrderFixture.getId(),
                testProduct,
                productOrderFixture.getPrice(),
                testSeller,
                testBuyer,
                productFixture.getStatus(),
                "예약중"
        );

        //when
        when(userAccountRepository.findById(userFixture.getUserId())).thenReturn(Optional.of(testBuyer));
        when(productOrderRepository.findById(productOrderFixture.getId())).thenReturn(Optional.of(testProductOrder));

        productOrderService.approve(productOrderFixture.getId(), testSeller.getUserId());
        //then
        assertEquals(0, testProduct.getQuantity());
        assertEquals("예약중", testProduct.getStatus());
        assertEquals("예약중", testProductOrder.getSellerStatus());
        assertEquals("예약중", testProductOrder.getBuyerStatus());
    }

    @DisplayName("주문량이 상품 재고를 초과할 경우 예외가 발생한다.")
    @Test
    public void givenSoldOutProduct_whenApproving_thenException() {
        //given
        UserInfoFixture.TestInfo userFixture = UserInfoFixture.get();
        ProductInfoFixture.TestInfo productFixture = ProductInfoFixture.get();
        ProductOrderInfoFixture.TestInfo productOrderFixture = ProductOrderInfoFixture.get();

        UserAccount testSeller = UserAccountFixture.get(userFixture.getUserId(), userFixture.getPassword(), userFixture.getUserName());
        UserAccount testBuyer = UserAccountFixture.get(userFixture.getUserId(), userFixture.getPassword(), userFixture.getUserName());
        Product testProduct = ProductFixture.get(productFixture.getId(),
                productFixture.getName(),
                productFixture.getPrice(),
                productFixture.getStatus(),
                0, testSeller
        );
        ProductOrder testProductOrder = ProductOrderFixture.get(
                productOrderFixture.getId(),
                testProduct,
                productOrderFixture.getPrice(),
                testSeller,
                testBuyer,
                productFixture.getStatus(),
                "예약중"
        );

        //when
        when(userAccountRepository.findById(userFixture.getUserId())).thenReturn(Optional.of(testBuyer));
        when(productOrderRepository.findById(productOrderFixture.getId())).thenReturn(Optional.of(testProductOrder));
        StudyApplicationException exception = Assertions.assertThrows(StudyApplicationException.class,
                () -> productOrderService.approve(productOrderFixture.getId(), testSeller.getUserId())
        );
        //then
        assertEquals(ErrorCode.PRODUCT_SOLD_OUT,exception.getErrorCode());
    }

    @DisplayName("판매 승인된 상품을 구매확정 하면 성공한다.")
    @Test
    public void givenApprovedProductOrder_whenConfirming_thenSuccess() {
        //given
        UserInfoFixture.TestInfo userFixture = UserInfoFixture.get();
        ProductInfoFixture.TestInfo productFixture = ProductInfoFixture.get();
        ProductOrderInfoFixture.TestInfo productOrderFixture = ProductOrderInfoFixture.get();

        UserAccount testSeller = UserAccountFixture.get(userFixture.getUserId(), userFixture.getPassword(), userFixture.getUserName());
        UserAccount testBuyer = UserAccountFixture.get(userFixture.getUserId(), userFixture.getPassword(), userFixture.getUserName());
        Product testProduct = ProductFixture.get(productFixture.getId(),
                productFixture.getName(),
                productFixture.getPrice(),
                productFixture.getStatus(),
                productFixture.getQuantity(), testSeller
        );
        ProductOrder testProductOrder = ProductOrderFixture.get(
                productOrderFixture.getId(),
                testProduct,
                productOrderFixture.getPrice(),
                testSeller,
                testBuyer,
                "예약중",
                "예약중"
        );

        //when
        when(userAccountRepository.findById(userFixture.getUserId())).thenReturn(Optional.of(testBuyer));
        when(productOrderRepository.findById(productOrderFixture.getId())).thenReturn(Optional.of(testProductOrder));

        productOrderService.confirm(productOrderFixture.getId(), testBuyer.getUserId());
        //then
        assertEquals("판매중",testProduct.getStatus());
        assertEquals("완료", testProductOrder.getSellerStatus());
        assertEquals("완료", testProductOrder.getBuyerStatus());
    }

    @DisplayName("판매 승인된 상품을 구매확정 하면 성공한다.(마지막 주문)")
    @Test
    public void givenLastApprovedProductOrder_whenConfirming_thenSuccess() {
        //given
        UserInfoFixture.TestInfo userFixture = UserInfoFixture.get();
        ProductInfoFixture.TestInfo productFixture = ProductInfoFixture.get();
        ProductOrderInfoFixture.TestInfo productOrderFixture = ProductOrderInfoFixture.get();

        UserAccount testSeller = UserAccountFixture.get(userFixture.getUserId(), userFixture.getPassword(), userFixture.getUserName());
        UserAccount testBuyer = UserAccountFixture.get(userFixture.getUserId(), userFixture.getPassword(), userFixture.getUserName());
        Product testProduct = ProductFixture.get(productFixture.getId(),
                productFixture.getName(),
                productFixture.getPrice(),
                "예약중",
                0, testSeller
        );
        ProductOrder testProductOrder = ProductOrderFixture.get(
                productOrderFixture.getId(),
                testProduct,
                productOrderFixture.getPrice(),
                testSeller,
                testBuyer,
                "예약중",
                "예약중"
        );

        //when
        when(userAccountRepository.findById(userFixture.getUserId())).thenReturn(Optional.of(testBuyer));
        when(productOrderRepository.findById(productOrderFixture.getId())).thenReturn(Optional.of(testProductOrder));

        productOrderService.confirm(productOrderFixture.getId(), testBuyer.getUserId());
        //then
        assertEquals("완료",testProduct.getStatus());
        assertEquals("완료", testProductOrder.getSellerStatus());
        assertEquals("완료", testProductOrder.getBuyerStatus());
    }

    @DisplayName("승인되지 않은 주문, 혹은 완료된 주문을 구매 확정 시 예외가 발생한다.")
    @Test
    public void givenAbnormalApprovedProductOrder_whenConfirming_thenException() {
        //given
        UserInfoFixture.TestInfo userFixture = UserInfoFixture.get();
        ProductInfoFixture.TestInfo productFixture = ProductInfoFixture.get();
        ProductOrderInfoFixture.TestInfo productOrderFixture = ProductOrderInfoFixture.get();

        UserAccount testSeller = UserAccountFixture.get(userFixture.getUserId(), userFixture.getPassword(), userFixture.getUserName());
        UserAccount testBuyer = UserAccountFixture.get(userFixture.getUserId(), userFixture.getPassword(), userFixture.getUserName());
        Product testProduct = ProductFixture.get(productFixture.getId(),
                productFixture.getName(),
                productFixture.getPrice(),
                "예약중",
                0, testSeller
        );
        ProductOrder testProductOrder = ProductOrderFixture.get(
                productOrderFixture.getId(),
                testProduct,
                productOrderFixture.getPrice(),
                testSeller,
                testBuyer,
                "예약중",
                ""
        );

        //when
        when(userAccountRepository.findById(userFixture.getUserId())).thenReturn(Optional.of(testBuyer));
        when(productOrderRepository.findById(productOrderFixture.getId())).thenReturn(Optional.of(testProductOrder));

        StudyApplicationException exception = Assertions.assertThrows(StudyApplicationException.class,
                () -> productOrderService.confirm(productOrderFixture.getId(), testBuyer.getUserId())
        );
        //then
        assertEquals(ErrorCode.PRODUCT_ORDER_STATUS_ABNORMAL,exception.getErrorCode());
    }





}
