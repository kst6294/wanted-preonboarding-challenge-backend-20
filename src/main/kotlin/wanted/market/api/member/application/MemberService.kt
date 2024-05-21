package wanted.market.api.member.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wanted.market.api.member.domain.dto.`in`.CommandRegisterMember
import wanted.market.api.member.domain.dto.out.CommandRegisterMemberResult
import wanted.market.api.member.domain.dto.out.RetrieveMemberResult
import wanted.market.api.member.domain.entity.Member
import wanted.market.api.member.repository.MemberRepository

@Service
@Transactional
class MemberService(
    private val memberRepository: MemberRepository
) {
    fun registerMember(request: CommandRegisterMember) : CommandRegisterMemberResult {
        val member = Member.register(request.userId, request.password)
        memberRepository.save(member)
        return CommandRegisterMemberResult(
            userId = member.userId,
            password = member.password
        )
    }

    fun findAllMembers(): List<RetrieveMemberResult> {
        val members = memberRepository.findAll()
        return members.map { RetrieveMemberResult.from(it) }
    }
}