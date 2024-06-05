package wanted.market.member.service

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.transaction.Transactional
import org.springframework.boot.test.context.SpringBootTest
import wanted.market.common.exception.ErrorCode
import wanted.market.member.dto.SaveMemberRequest
import wanted.market.member.entity.Member
import wanted.market.member.exception.MemberException
import wanted.market.member.repository.MemberRepository
import java.util.*


class MemberServiceTest: BehaviorSpec( {

    val memberRepository = mockk<MemberRepository>(relaxed = true)
    val memberService = MemberService(memberRepository)
    val memberId = 123L
    val saveMemberRequest = SaveMemberRequest("노을", "sunset@gmail.com")
    val existingMember = Member("노을", "sunset@gmail.com", memberId)

    given("회원가입 테스트") {

        When ("이메일이 중복되지 않으면") {
            every { memberRepository.findByEmail(saveMemberRequest.email) } returns Optional.empty<Member>()
            every { memberRepository.save(existingMember) } returns existingMember
            then("회원가입에 성공한다.") {
//                memberService.save(saveMemberRequest)
                shouldNotThrow<MemberException> { memberService.save(saveMemberRequest) }
                verify(exactly = 1) { memberRepository.save(existingMember) }
                verify(exactly = 1) { memberRepository.findByEmail(existingMember.email) }
            }
        }

        When ("이메일이 중복되면") {

            every { memberRepository.findByEmail(existingMember.email) } returns Optional.of(existingMember)

            then("회원가입에 실패한다.") {
                val exception = shouldThrow<MemberException> {
                    memberService.save(saveMemberRequest)
                }
                exception.errorCode shouldBe ErrorCode.DUPLICATE_EMAIL
            }
        }
    }


    given("회원 조회 테스트 "){

        When ("존재하지 않는 id로 멤버 조회하면") {
            every { memberRepository.findById(1L) } returns Optional.empty()

            then("Exception을 던진다.") {
                val exception = shouldThrow<MemberException> {
                    memberService.findMember(1L)
                }
                exception.errorCode shouldBe ErrorCode.MEMBER_NOT_FOUND
            }
        }
    }
})

