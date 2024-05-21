package wanted.market.api.member.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
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
@Tag(name = "01. 회원 인증 API", description = "인증/권한 없이 접근할 수 있는 회원 API")
class MemberAuthController(
    private val memberAuthService: MemberAuthService
) {

    @Operation(
        summary = "회원가입", responses = [
            ApiResponse(responseCode = "400", description = "이미 존재하는 아이디입니다."),
            ApiResponse(responseCode = "200", description = "회원가입 성공")
        ]
    )
    @PostMapping
    fun registerMember(@RequestBody request: CommandRegisterMember) : ApiResultResponse<CommandRegisterMemberResult> {
        return ApiResultResponse(
            data = memberAuthService.registerMember(request)
        )
    }

    @Operation(
        summary = "로그인", responses = [
            ApiResponse(responseCode = "400", description = "존재하지 않는 아이디입니다."),
            ApiResponse(responseCode = "200", description = "로그인 성공")
        ]
    )
    @PostMapping("/login")
    fun loginMember(@RequestBody request: CommandLoginMember) : ApiResultResponse<Tokens> {
        return ApiResultResponse(
            data = memberAuthService.loginMember(request)
        )
    }
}