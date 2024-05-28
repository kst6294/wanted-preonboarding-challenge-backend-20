package wanted.market.common.exception


data class ErrorResponse(val statusCode: Int, val message: String) {

    companion object {
        fun of (statusCode: Int, message: String): ErrorResponse {
            return ErrorResponse(statusCode, message)
        }
    }
}