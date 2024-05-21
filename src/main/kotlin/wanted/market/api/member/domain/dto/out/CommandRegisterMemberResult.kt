package wanted.market.api.member.domain.dto.out

data class CommandRegisterMemberResult(
    val id: Long,
    val userId: String,
    val password: String
)
