package wanted.market.member.exception

import wanted.market.common.ErrorCode

class MemberException(private val errorCode: ErrorCode) : RuntimeException(errorCode.message) {
}