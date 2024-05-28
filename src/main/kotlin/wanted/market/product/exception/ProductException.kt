package wanted.market.product.exception

import wanted.market.common.ErrorCode

class ProductException(errorCode: ErrorCode): RuntimeException(errorCode.message) {
}