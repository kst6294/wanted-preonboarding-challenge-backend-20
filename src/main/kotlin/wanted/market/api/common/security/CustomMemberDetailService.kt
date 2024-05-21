package wanted.market.api.common.security

import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import wanted.market.api.member.repository.MemberRepository

@Service
class CustomMemberDetailService(
    private val memberRepository: MemberRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        return memberRepository.findByIdOrNull(username?.toLong())?.let {
            CustomMemberDetails(it)
        } ?: throw IllegalArgumentException("회원을 찾을 수 없습니다.")
    }
}
