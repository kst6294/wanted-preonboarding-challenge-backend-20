package wanted.market.api.member.application

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import wanted.market.api.member.domain.dto.`in`.CommandLoginMember
import wanted.market.api.member.domain.dto.`in`.CommandRegisterMember
import wanted.market.api.member.domain.dto.out.CommandRegisterMemberResult

@SpringBootTest
@Transactional
class MemberAuthServiceTest(
    private val memberAuthService: MemberAuthService
) : StringSpec({

    fun registerMember(): CommandRegisterMemberResult {
        val request = CommandRegisterMember(
            userId = "seonwoo_jung",
            password = "12345678a"
        )

        // when
        return memberAuthService.registerMember(request)
    }

    "회원가입을 한다" {
        // given
        val request = CommandRegisterMember(
            userId = "seonwoo_jung",
            password = "12345678a"
        )

        // when
        val response = memberAuthService.registerMember(request)

        // then
        request.userId shouldBe response.userId
        request.password shouldBe response.password
    }

    "로그인을 한다" {
        // given
        val memberInfo = registerMember()
        val loginRequest = CommandLoginMember(
            userId = memberInfo.userId,
            password = memberInfo.password
        )

        // when
        val token = memberAuthService.loginMember(loginRequest)

        // then
        token.userId shouldBe memberInfo.userId
        token.accessToken shouldNotBe null
    }

})
