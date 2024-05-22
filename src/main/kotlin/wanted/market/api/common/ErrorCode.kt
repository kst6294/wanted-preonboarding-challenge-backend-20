package wanted.market.api.common

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val code: String,
    val message: String
) {
    HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "C_003", "권한이 없습니다."),
}
