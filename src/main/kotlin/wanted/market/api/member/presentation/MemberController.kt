package wanted.market.api.member.presentation

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import wanted.market.api.common.ApiResultResponse
import wanted.market.api.member.application.MemberService
import wanted.market.api.member.domain.dto.out.RetrieveMemberResult

@RestController
@RequestMapping("/members")
class MemberController(
    private val memberService: MemberService
) {

    @GetMapping
    fun findAllMembers(): ApiResultResponse<List<RetrieveMemberResult>> {
        return ApiResultResponse(
            data = memberService.findAllMembers()
        )
    }
}