package wanted.market.api.member.domain.dto.out

import wanted.market.api.member.domain.entity.Member

data class RetrieveMemberResult(
    val userId: String
) {
    companion object {
        fun from(member: Member): RetrieveMemberResult {
            return RetrieveMemberResult(userId = member.userId)
        }
    }
}
