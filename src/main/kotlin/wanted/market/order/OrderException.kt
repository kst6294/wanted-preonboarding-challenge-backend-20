package wanted.market.order

import wanted.market.common.exception.BaseException
import wanted.market.common.exception.ErrorCode

class OrderException(errorCode: ErrorCode) : BaseException(errorCode){
}