package wanted.market.order.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import wanted.market.order.service.OrderService

@RestController
@RequestMapping("/orders")
class OrderController(@Autowired private val orderService: OrderService) {


    @PostMapping("/products/{productId}")
    fun order(@RequestParam buyerId: Long, @PathVariable productId: Long): ResponseEntity<Void> {
        orderService.order(buyerId, productId)
        return ResponseEntity.ok().build()
    }

    @PutMapping("/{orderId}/approve")
    fun acceptOrder(@RequestParam sellerId: Long, @PathVariable orderId: Long): ResponseEntity<Void> {
        orderService.approveOrder(sellerId, orderId)
        return ResponseEntity.ok().build()
    }
}