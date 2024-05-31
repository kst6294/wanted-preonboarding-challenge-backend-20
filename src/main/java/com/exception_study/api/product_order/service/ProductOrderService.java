package com.exception_study.api.product_order.service;

import com.exception_study.api.product.entity.Product;
import com.exception_study.api.product_order.dto.ProductOrderDto;
import com.exception_study.api.product_order.dto.response.HistoryResponse;
import com.exception_study.api.product_order.entity.ProductOrder;
import com.exception_study.api.product_order.repository.ProductOrderRepository;
import com.exception_study.api.user_account.entity.UserAccount;
import com.exception_study.global.exception.ErrorCode;
import com.exception_study.global.exception.StudyApplicationException;
import com.exception_study.api.product.repository.ProductRepository;
import com.exception_study.api.user_account.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * TODO: 2개의 주문이 예약중인 상태(quantity == 0)일 떄 만약 둘중 하나의 주문이라도 체결되면 판매가 완료 되는 현상을 해결해야함.
 * TODO: 아마 주문을 마감하기 전에, 해당 Product에 대한 주문(ProductOrder)가 있을 경우에 status를 변경하지 않도록 하는 로직이 필요
 */
@Transactional
@RequiredArgsConstructor
@Service
public class ProductOrderService {

    private final UserAccountRepository userAccountRepository;
    private final ProductRepository productRepository;
    private final ProductOrderRepository productOrderRepository;


    public ProductOrderDto reserve(Long productId, String userId) {
        UserAccount buyer = userAccountRepository.findById(userId).orElseThrow(
                () -> new StudyApplicationException(ErrorCode.USER_NOT_FOUND)
        );
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new StudyApplicationException(ErrorCode.PRODUCT_NOT_FOUND)
        );

        ProductOrder orderCheck = productOrderRepository.findByIdAndBuyer(productId, buyer);
        if (orderCheck != null) {
            throw new StudyApplicationException(ErrorCode.PRODUCT_ALREADY_RESERVED);
        }

        int current_quantity = product.getQuantity();
        if (current_quantity <= 0) {
            throw new StudyApplicationException(ErrorCode.PRODUCT_SOLD_OUT);
        }

        ProductOrder productOrder = ProductOrder.of(null, product, product.getPrice(), product.getSeller(), buyer, "판매중", null);
        productOrderRepository.save(productOrder);
        return ProductOrderDto.from(productOrder);
    }

    public void approve(Long id, String userId) {
        UserAccount seller = userAccountRepository.findById(userId).orElseThrow(
                () -> new StudyApplicationException(ErrorCode.USER_NOT_FOUND)
        );
        ProductOrder productOrder = productOrderRepository.findById(id).orElseThrow(
                () -> new StudyApplicationException(ErrorCode.PRODUCT_NOT_FOUND)
        );
        if(!productOrder.getSeller().equals(seller)){
            throw new StudyApplicationException(ErrorCode.INVALID_PERMISSION);
        }
        Product product = productOrder.getProduct();
        int current_quantity = product.getQuantity();
        if (current_quantity == 1) {
            product.setQuantity(0);
            productOrder.setSellerStatus("예약중");
            product.setStatus("예약중");
        } else if (current_quantity < 1) {
            throw new StudyApplicationException(ErrorCode.PRODUCT_SOLD_OUT);
        }else{
            product.setQuantity(current_quantity -1);
        }
        productOrder.setSellerStatus("예약중");
        productOrder.setBuyerStatus("예약중");
    }

    public void confirm(Long id, String userId) {
        UserAccount buyer = userAccountRepository.findById(userId).orElseThrow(
                () -> new StudyApplicationException(ErrorCode.USER_NOT_FOUND)
        );
        ProductOrder productOrder = productOrderRepository.findById(id).orElseThrow(
                () -> new StudyApplicationException(ErrorCode.PRODUCT_NOT_FOUND)
        );
        if(!productOrder.getBuyer().equals(buyer)){
            throw new StudyApplicationException(ErrorCode.INVALID_PERMISSION);
        }
        if(productOrder.getSellerStatus().equals("완료")){
            throw new StudyApplicationException(ErrorCode.PRODUCT_ORDER_ALREADY_CONFIRMED);
        }
        Product product = productOrder.getProduct();
        int current_quantity = product.getQuantity();
        productOrder.setSellerStatus("완료");
        productOrder.setBuyerStatus("완료");
        if(current_quantity == 0){
            boolean flag = false;
            List<ProductOrder> orders = productOrderRepository.findAllByProductName(product.getName());
            for (ProductOrder order : orders) {
                String sellerStatus = order.getSellerStatus();
                if (sellerStatus.equals("예약중")) {
                    flag = true;
                    break;
                }
            }
            if(!flag){
                product.setStatus("완료");
            }
        }
    }


    public HistoryResponse history(String userId) {
        List<ProductOrderDto> buyList = productOrderRepository.findAllByBuyer(userId).stream().map(ProductOrderDto::from).toList();
        List<ProductOrderDto> reservedList = productOrderRepository.findReservedProducts(userId).stream().map(ProductOrderDto::from).toList();

        return HistoryResponse.of(buyList, reservedList);
    }


}
