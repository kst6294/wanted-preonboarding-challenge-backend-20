package wanted.market.api.domain.member.service

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import wanted.market.api.domain.member.dto.RegisterMemberRequest
import wanted.market.api.domain.member.dto.LoginMemberRequest
import wanted.market.api.domain.member.entity.Member
import wanted.market.api.domain.member.repository.MemberRepository

@Service
@Transactional
class MemberService(
    @Autowired private val memberRepository: MemberRepository
) {
    fun registerMember(registerMemberRequest: RegisterMemberRequest) {
        val newMember = Member(
            email = registerMemberRequest.email,
            password = registerMemberRequest.password
        )
        memberRepository.save(newMember)
    }

    fun loginMember(loginMemberRequest: LoginMemberRequest) {
        memberRepository.findByEmailAndPassword(email= loginMemberRequest.email, password= loginMemberRequest.password)
    }

    fun findMember(memberId: Long): Member {
        val member = memberRepository.findById(memberId)
            .orElseThrow{ RuntimeException() }
        return member
    }
}
