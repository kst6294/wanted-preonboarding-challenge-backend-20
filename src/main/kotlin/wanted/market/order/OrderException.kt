package wanted.market.order

import wanted.market.common.ErrorCode

class OrderException(private val errorCode: ErrorCode) : RuntimeException(errorCode.message){
}