package wanted.market.member.exception

import wanted.market.common.exception.BaseException
import wanted.market.common.exception.ErrorCode

class MemberException(errorCode: ErrorCode) : BaseException(errorCode) {
}