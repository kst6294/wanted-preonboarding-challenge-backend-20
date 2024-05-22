package wanted.market.product

import wanted.market.ErrorCode
import java.lang.Error

class ProductException(errorCode: ErrorCode): RuntimeException(errorCode.message) {
}