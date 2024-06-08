package com.api.jellomarket.exception

import com.api.jellomarket.enums.error.ErrorCodeCustom

class BusinessException(
    val errorCode: ErrorCodeCustom,
    val errorMessage: String,
) : RuntimeException() {
    companion object {
        fun of(errorCodeCustom: ErrorCodeCustom): BusinessException {
            return BusinessException(errorCodeCustom, errorCodeCustom.message)
        }
        fun of(errorCodeCustom: ErrorCodeCustom, errorMessage: String): BusinessException {
            return BusinessException(errorCodeCustom, errorMessage)
        }
    }
}