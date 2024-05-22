package wanted.market.member

import wanted.market.ErrorCode

class MemberException(private val errorCode: ErrorCode) : RuntimeException(errorCode.message) {
}