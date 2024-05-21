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
        roles.add(SimpleGrantedAuthority("ROLE_MEMBER"))
        return roles
    }

    override fun getPassword(): String {
        return member.password
    }

    override fun getUsername(): String {
        return member.userId
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    fun getMemberId(): Long {
        return member.id!!
    }
}
