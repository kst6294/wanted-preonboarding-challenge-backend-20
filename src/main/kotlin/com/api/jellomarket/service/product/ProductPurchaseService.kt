package com.api.jellomarket.service.product

import com.api.jellomarket.domain.order.Order
import com.api.jellomarket.domain.order.OrderRepository
import com.api.jellomarket.domain.product.ProductRepository
import com.api.jellomarket.domain.user.User
import com.api.jellomarket.dto.product.ProductPurchaseRequestDTO
import com.api.jellomarket.dto.product.ProductPurchaseResponseDTO
import com.api.jellomarket.enums.error.ErrorCodeCustom
import com.api.jellomarket.enums.order.OrderStatus
import com.api.jellomarket.exception.BusinessException
import com.api.jellomarket.utils.JelloUtils
import com.api.jellomarket.validation.ProductPurchaseValidation
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Service
class ProductPurchaseService(
    val productRepository: ProductRepository,
    val orderRepository: OrderRepository,
) {

    /**
     * 상품 구매
     * @param user
     * @param productId
     * @param productPurchaseRequestDTO
     * @return ProductPurchaseResponseDTO
     */
    @Transactional
    fun purchaseProduct(user: User?, productId: Long, productPurchaseRequestDTO: ProductPurchaseRequestDTO): ProductPurchaseResponseDTO {
        var newOrderStatus = OrderStatus.NEW_ORDER
        // validation
        val product = ProductPurchaseValidation(productRepository).validateProductPurchase(user, productId)
        if (product.stock <= 0) {
            // 예약중인 주문이 있는지 확인
            val isOnReversed = orderRepository.countByOrderStatus(OrderStatus.ON_RESERVED)
            if (isOnReversed > 0) {
                throw BusinessException.of(ErrorCodeCustom.SOLD_OUT)
            } else {
                newOrderStatus = OrderStatus.ON_RESERVED
            }
        }
        // 구매 진행한다.
        product.stock -= 1
        val newOrder = orderRepository.save(
            Order(
                userId = user!!.userId!!,
                productId = product.productId!!,
                status = newOrderStatus,
                quantity = productPurchaseRequestDTO.quantity,
                price = productPurchaseRequestDTO.price,
                totalAmount = productPurchaseRequestDTO.quantity * productPurchaseRequestDTO.price.toFloat(),
                createdAt = JelloUtils().getCurrentDateTime(),
            )
        )
        productRepository.save(product)
        val res = ProductPurchaseResponseDTO(
            orderId = newOrder.orderId!!,
            status = newOrder.status,
            orderDate = newOrder.createdAt,
        )
        return res
    }
}