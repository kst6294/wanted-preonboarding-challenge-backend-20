package wanted.market.member

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/members")
class MemberController(
    @Autowired private val memberService: MemberService
) {

    //회원가입
    @PostMapping
    fun saveMember(
        @RequestBody saveMemberRequest: SaveMemberRequest): ResponseEntity<Any> {
        val memberId = memberService.save(saveMemberRequest)
        return ResponseEntity.ok().body(memberId)
    }
}