package wanted.market.api.member.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wanted.market.api.member.domain.dto.out.RetrieveMemberResult
import wanted.market.api.member.repository.MemberRepository

@Service
@Transactional
class MemberService(
    private val memberRepository: MemberRepository
) {
    fun findAllMembers(): List<RetrieveMemberResult> {
        val members = memberRepository.findAll()
        return members.map { RetrieveMemberResult.from(it) }
    }
}