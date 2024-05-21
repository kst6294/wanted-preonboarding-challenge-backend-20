package wanted.market.api.member.presentation

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import wanted.market.api.common.ApiResultResponse
import wanted.market.api.member.application.MemberAuthService
import wanted.market.api.member.domain.dto.`in`.CommandLoginMember
import wanted.market.api.member.domain.dto.`in`.CommandRegisterMember
import wanted.market.api.member.domain.dto.out.CommandRegisterMemberResult
import wanted.market.api.member.domain.entity.Tokens

@RestController
@RequestMapping("/auth/members")
class MemberAuthController(
    private val memberAuthService: MemberAuthService
) {

    @PostMapping
    fun registerMember(@RequestBody request: CommandRegisterMember) : ApiResultResponse<CommandRegisterMemberResult> {
        return ApiResultResponse(
            data = memberAuthService.registerMember(request)
        )
    }

    @PostMapping("/login")
    fun loginMember(@RequestBody request: CommandLoginMember) : ApiResultResponse<Tokens> {
        return ApiResultResponse(
            data = memberAuthService.loginMember(request)
        )
    }
}