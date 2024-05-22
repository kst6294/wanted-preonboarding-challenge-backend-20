package wanted.market.api.order.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import wanted.market.api.common.ApiResultResponse
import wanted.market.api.common.security.CustomMemberDetails
import wanted.market.api.order.application.OrderService
import wanted.market.api.product.domain.dto.out.CommandApproveOrderResult

@RestController
@RequestMapping("/order")
@Tag(name = "05. 주문 API", description = "인증 또는 권한 부여 후에 접근할 수 있는 주문 API")
class OrderController(
    private val orderService: OrderService
) {

    @Operation(
        summary = "판매 승인", responses = [
            ApiResponse(responseCode = "400", description = "주문을 찾을 수 없음"),
            ApiResponse(responseCode = "200", description = "판매 승인 성공")
        ]
    )
    @PostMapping("/{orderId}")
    fun approveOrder(@PathVariable orderId: Long,
                     @AuthenticationPrincipal member: CustomMemberDetails) : ApiResultResponse<CommandApproveOrderResult> {
        return ApiResultResponse(
            data = orderService.approveOrder(orderId, member.getMemberId())
        )
    }

    @Operation(
        summary = "구매 확정", responses = [
            ApiResponse(responseCode = "400", description = "주문을 찾을 수 없음"),
            ApiResponse(responseCode = "200", description = "구매 확정 성공")
        ]
    )
    @PostMapping("/confirm/{orderId}")
    fun confirmOrder(@PathVariable orderId: Long,
                     @AuthenticationPrincipal member: CustomMemberDetails) : ApiResultResponse<Long> {
        return ApiResultResponse(
            data = orderService.confirmOrder(orderId, member.getMemberId())
        )
    }
}