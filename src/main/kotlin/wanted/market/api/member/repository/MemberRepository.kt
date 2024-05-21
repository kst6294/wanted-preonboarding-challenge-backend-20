package wanted.market.api.member.repository

import org.springframework.data.jpa.repository.JpaRepository
import wanted.market.api.member.domain.entity.Member

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByUserIdAndPassword(userId: String, password: String): Member?
    fun findByUserId(userId: String): Member?
}