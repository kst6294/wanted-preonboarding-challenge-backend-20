package wanted.market.api.common.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import wanted.market.api.member.domain.entity.Member

class CustomMemberDetails(
    private val member: Member
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val roles = mutableListOf<GrantedAuthority>()
        roles.add(SimpleGrantedAuthority("ROLE_USER"))
        return roles
    }

    override fun getPassword(): String {
        return member.password
    }

    override fun getUsername(): String {
        return member.userId
    }

    fun getMemberId(): Long {
        return member.id!!
    }

    override fun isAccountNonExpired(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isAccountNonLocked(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isCredentialsNonExpired(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEnabled(): Boolean {
        TODO("Not yet implemented")
    }
}
