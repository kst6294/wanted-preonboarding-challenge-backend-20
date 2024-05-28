package wanted.market.product.exception

import wanted.market.common.exception.BaseException
import wanted.market.common.exception.ErrorCode

class ProductException(errorCode: ErrorCode): BaseException(errorCode) {
}