package wanted.market.product

import wanted.market.ErrorCode

class ProductException(errorCode: ErrorCode): RuntimeException(errorCode.message) {
}