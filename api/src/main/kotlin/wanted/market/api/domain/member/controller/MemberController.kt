package wanted.market.api.domain.member.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import wanted.market.api.domain.member.service.MemberService
import wanted.market.api.domain.member.dto.RegisterMemberRequest
import wanted.market.api.domain.member.dto.LoginMemberRequest

@RestController
@RequestMapping("/auth")
class MemberController(
    @Autowired private val memberService: MemberService
) {
    @PostMapping("/register")
    fun registerMember(
        @RequestBody registerMemberRequest: RegisterMemberRequest
    ): ResponseEntity<Any> {
        memberService.registerMember(registerMemberRequest)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/login")
    fun loginMember(
      @RequestBody loginMemberRequest: LoginMemberRequest
    ): ResponseEntity<Any> {
        memberService.loginMember(loginMemberRequest)
        return ResponseEntity.ok().build()
    }
}
