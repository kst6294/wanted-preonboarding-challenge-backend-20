package wanted.market.api.order.presentation

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import wanted.market.api.common.ApiResultResponse
import wanted.market.api.common.security.CustomMemberDetails
import wanted.market.api.order.application.OrderService
import wanted.market.api.product.domain.dto.out.CommandApproveOrderResult

@RestController
@RequestMapping("/order")
class OrderController(
    private val orderService: OrderService
) {

    @RequestMapping("/{orderId}")
    fun approveOrder(@PathVariable orderId: Long,
                     @AuthenticationPrincipal member: CustomMemberDetails) : ApiResultResponse<CommandApproveOrderResult> {
        return ApiResultResponse(
            data = orderService.approveOrder(orderId, member.getMemberId())
        )
    }
}