package wanted.market.api.member.presentation

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import wanted.market.api.common.ApiResultResponse
import wanted.market.api.member.application.MemberService
import wanted.market.api.member.domain.dto.`in`.CommandRegisterMember
import wanted.market.api.member.domain.dto.out.CommandRegisterMemberResult
import wanted.market.api.member.domain.dto.out.RetrieveMemberResult

@RestController
@RequestMapping("/members")
class MemberController(
    private val memberService: MemberService
) {
    @PostMapping
    fun registerMember(@RequestBody request: CommandRegisterMember) : ApiResultResponse<CommandRegisterMemberResult> {
        return ApiResultResponse(
            data = memberService.registerMember(request)
        )
    }

    @GetMapping
    fun findAllMembers() : ApiResultResponse<List<RetrieveMemberResult>> {
        return ApiResultResponse(
            data = memberService.findAllMembers()
        )
    }
}