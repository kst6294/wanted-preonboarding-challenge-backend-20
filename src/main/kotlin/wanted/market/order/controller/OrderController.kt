package wanted.market.order.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import wanted.market.order.service.OrderService

@RestController
@RequestMapping("/orders")
class OrderController(@Autowired private val orderService: OrderService) {


    @PostMapping
    fun order(@RequestParam memberId: Long, @RequestParam productId: Long): ResponseEntity<Void> {
        return ResponseEntity.ok().build()
    }

    @PutMapping
    fun acceptOrder(): ResponseEntity<Void> {
        return ResponseEntity.ok().build()
    }
}