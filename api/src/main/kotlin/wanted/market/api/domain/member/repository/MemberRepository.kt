package wanted.market.api.domain.member.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import wanted.market.api.domain.member.entity.Member

@Repository
interface MemberRepository : JpaRepository<Member, Long> {
    fun findByEmailAndPassword(email: String, password: String): Member?
    fun findByEmail(email: String): Member?
}
