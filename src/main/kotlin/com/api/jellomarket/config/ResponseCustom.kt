package com.api.jellomarket.config

import com.api.jellomarket.enums.error.ErrorCodeCustom

open class ResponseCustom(
    open val message: String = "",
    open val data: Any? = null,
    open val errorDetail: ErrorDetail? = null
) {
    data class Success(
        override val data: Any? = null,
        override val message: String = "success",
        ) : ResponseCustom()

    data class Error(
        override val message: String = "fail",
        override val data: Any? = null,
        override val errorDetail: ErrorDetail? = null
    ) : ResponseCustom()

    data class ErrorDetail(
        val detailCode: ErrorCodeCustom,
        val detailMessage: String,
        var systemMessage: String? = null
    ) {
        companion object {
            fun of(detailCode: ErrorCodeCustom): ErrorDetail {
                return ErrorDetail(detailCode, detailCode.message)
            }
        }
    }
}