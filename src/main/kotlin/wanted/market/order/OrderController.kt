package wanted.market.order

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/orders")
class OrderController(@Autowired private val orderService: OrderService) {


    @PostMapping
    fun order(): ResponseEntity<Void> {
        return ResponseEntity.ok().build()
    }
}