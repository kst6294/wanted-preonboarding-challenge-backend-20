package wanted.market.api.common.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import wanted.market.api.member.repository.MemberRepository

@Service
class CustomMemberDetailService(
    private val memberRepository: MemberRepository
) : UserDetailsService {

    override fun loadUserByUsername(userId: String): UserDetails {
        return memberRepository.findById(userId.toLong())
            .map { CustomMemberDetails(it) }
            .orElseThrow { IllegalArgumentException("회원을 찾을 수 없습니다.") }
    }
}
