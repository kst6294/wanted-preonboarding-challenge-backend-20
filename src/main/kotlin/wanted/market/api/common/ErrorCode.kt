package wanted.market.api.common

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val code: String,
    val message: String
) {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "C_000", "회원이 존재하지 않습니다."),
    MEMBER_EMAIL_DUPLICATION(HttpStatus.BAD_REQUEST, "C_001", "이미 가입된 이메일 주소입니다."),
    ACCESS_TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "C_002", "토큰이 만료되었습니다."),
    HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "C_003", "권한이 없습니다."),
    ACCESS_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "C_004", "토큰을 찾을 수 없습니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "C_005", "갱신 토큰이 만료되었습니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "C_006", "갱신 토큰을 찾을 수 없습니다.")
}
