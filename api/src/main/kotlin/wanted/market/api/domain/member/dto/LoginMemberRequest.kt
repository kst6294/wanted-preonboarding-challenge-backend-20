package wanted.market.api.domain.member.dto

data class LoginMemberRequest(
    var email: String,
    var password: String
)
