package com.exception_study.api.product_order.service;

import com.exception_study.api.product.entity.Product;
import com.exception_study.api.product_order.dto.ProductOrderDto;
import com.exception_study.api.product_order.dto.response.HistoryResponse;
import com.exception_study.api.product_order.entity.ProductOrder;
import com.exception_study.api.product_order.repository.ProductOrderRepository;
import com.exception_study.user_account.entity.UserAccount;
import com.exception_study.global.exception.ErrorCode;
import com.exception_study.global.exception.StudyApplicationException;
import com.exception_study.api.product.repository.ProductRepository;
import com.exception_study.user_account.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class ProductOrderService {

    private final UserAccountRepository userAccountRepository;
    private final ProductRepository productRepository;
    private final ProductOrderRepository productOrderRepository;


    public ProductOrderDto reserve(int id, String userId) {
        UserAccount buyer = userAccountRepository.findById(userId).orElseThrow(
                () -> new StudyApplicationException(ErrorCode.USER_NOT_FOUND)
        );
        Product product = productRepository.findById(id).orElseThrow(
                () -> new StudyApplicationException(ErrorCode.PRODUCT_NOT_FOUND)
        );

        product.setStatus("예약중");

        ProductOrder productOrder = ProductOrder.of(null, product, product.getPrice(), product.getSeller(), buyer, "예약중", "예약중");
        productOrderRepository.save(productOrder);
        return ProductOrderDto.from(productOrder);
    }

    public void approve(int id, String userId) {
        UserAccount userAccount = userAccountRepository.findById(userId).orElseThrow(() -> new StudyApplicationException(ErrorCode.USER_NOT_FOUND));
        Product product = productRepository.findBySeller(userAccount);
        ProductOrder productOrder = productOrderRepository.findById(id).orElseThrow(
                () -> new StudyApplicationException(ErrorCode.PRODUCT_NOT_FOUND)
        );
        product.setStatus("완료");
        productOrder.setSellerStatus("완료");
        productOrder.setBuyerStatus("완료");
    }


    public HistoryResponse history(String userId) {
        UserAccount userAccount = userAccountRepository.findById(userId).orElseThrow(() -> new StudyApplicationException(ErrorCode.USER_NOT_FOUND));

        List<ProductOrderDto> buyList = productOrderRepository.findAllByBuyer(userId).stream().map(ProductOrderDto::from).toList();
        List<ProductOrderDto> reservedList = productOrderRepository.findReservedProducts(userId).stream().map(ProductOrderDto::from).toList();

        return HistoryResponse.of(buyList, reservedList);
    }


}
