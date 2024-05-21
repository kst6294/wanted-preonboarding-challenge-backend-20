package wanted.market.api.member.domain.dto.out

import wanted.market.api.member.domain.entity.Member
import java.time.LocalDateTime

data class RetrieveMemberResult(
    val userId: String,
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(member: Member): RetrieveMemberResult {
            return RetrieveMemberResult(
                userId = member.userId,
                createdAt = member.createdAt
            )
        }
    }
}
