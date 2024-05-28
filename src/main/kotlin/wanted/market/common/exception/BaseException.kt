package wanted.market.common.exception

open class BaseException(
    val errorCode: ErrorCode,
    override val message: String = errorCode.message
) : RuntimeException()