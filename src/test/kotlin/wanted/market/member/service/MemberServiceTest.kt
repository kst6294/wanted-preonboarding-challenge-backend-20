package wanted.market.member.service

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import wanted.market.common.exception.ErrorCode
import wanted.market.member.dto.SaveMemberRequest
import wanted.market.member.entity.Member
import wanted.market.member.exception.MemberException
import wanted.market.member.repository.MemberRepository
import java.util.*


class MemberServiceTest: BehaviorSpec( {

    val memberRepository = mockk<MemberRepository>()
    val memberService = MemberService(memberRepository)
    val memberId = 123L
    val saveMemberRequest = SaveMemberRequest("노을", "sunset@gmail.com")
    val existingMember = Member("노을", "sunset@gmail.com", memberId)

    given("이메일이 중복되지 않을 때") {
        When ("회원가입을 하면") {
            every { memberRepository.findByEmail(saveMemberRequest.email) } returns Optional.empty<Member>()
            every { memberRepository.save(any()) } returns existingMember
            then("멤버가 저장된다.") {
                memberService.save(saveMemberRequest)
                shouldNotThrow<MemberException> { memberService.save(saveMemberRequest) }
//                verify(exactly = 1) { memberRepository.save(any()) }
//                verify(exactly = 1) { memberRepository.findByEmail(any()) }
            }
        }
    }

    given ("이메일이 중복될 때") {
        every { memberRepository.findByEmail(existingMember.email) } returns Optional.of(existingMember)
        When("회원가입을 시도하면"){
            then("Exception을 던진다.") {
                val exception = shouldThrow<MemberException> {
                    memberService.save(saveMemberRequest)
                }
                exception.errorCode shouldBe ErrorCode.DUPLICATE_EMAIL
            }
        }
    }

    given("존재하지 않는 id로"){
        every { memberRepository.findById(1L) } returns Optional.empty()
        When ("회원 조회를 하면") {
            then("Exception을 던진다.") {
                val exception = shouldThrow<MemberException> {
                    memberService.findMember(1L)
                }
                exception.errorCode shouldBe ErrorCode.MEMBER_NOT_FOUND
            }
        }
    }
})

