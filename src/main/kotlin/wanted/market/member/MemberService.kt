package wanted.market.member

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import wanted.market.ErrorCode

@Service
@Transactional
class MemberService(
    @Autowired private val memberRepository: MemberRepository
) {
    fun save(saveMemberRequest: SaveMemberRequest) {
        checkDuplication(saveMemberRequest.email)
        joinMember(saveMemberRequest)
    }

    private fun joinMember(saveMemberRequest: SaveMemberRequest): Member {
        val member = Member(
            name = saveMemberRequest.name,
            email = saveMemberRequest.email
        )
        memberRepository.save(member)
        return member
    }

    private fun checkDuplication(email: String) {
        val existMember = memberRepository.findByEmail(email)
        if (existMember != null) {
            throw MemberException(ErrorCode.DUPLICATE_EMAIL)
        }
    }

    fun findMember(memberId: Long): Member {
        val member = memberRepository.findById(memberId)
            .orElseThrow{MemberException(ErrorCode.MEMBER_NOT_FOUND)}
        return member
    }


}