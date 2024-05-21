package wanted.market.api.common.security

import wanted.market.api.common.ErrorCode
import java.time.LocalDateTime

class ErrorResponse(
    var message: String,
    var code: String,
    var timestamp: String = LocalDateTime.now().toString()
) {

    companion object {
        fun of(errorCode: ErrorCode): ErrorResponse {
            return ErrorResponse(errorCode.message, errorCode.code)
        }

        fun of(errorMessage: String): ErrorResponse {
            return ErrorResponse(errorMessage, "400")
        }
    }
}
