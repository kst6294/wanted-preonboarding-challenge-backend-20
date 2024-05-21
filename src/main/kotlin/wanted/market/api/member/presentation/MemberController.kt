package wanted.market.api.member.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import wanted.market.api.common.ApiResultResponse
import wanted.market.api.member.application.MemberService
import wanted.market.api.member.domain.dto.out.RetrieveMemberResult

@RestController
@RequestMapping("/members")
@Tag(name = "02. 회원 API", description = "인증 또는 권한 부여 후에 접근할 수 있는 회원 API")
class MemberController(
    private val memberService: MemberService
) {

    @Operation(
        summary = "모든 회원 조회", responses = [
            ApiResponse(responseCode = "200", description = "모든 회원 조회 성공")
        ]
    )
    @GetMapping
    fun findAllMembers(): ApiResultResponse<List<RetrieveMemberResult>> {
        return ApiResultResponse(
            data = memberService.findAllMembers()
        )
    }
}