package com.api.jellomarket.validation

import com.api.jellomarket.domain.user.User
import com.api.jellomarket.dto.product.ProductCreateRequestDTO

class ProductValidation {

    fun validateProductCreateRequestDTO(user: User?, requestDTO: ProductCreateRequestDTO) {
        if (user == null) {
            throw IllegalArgumentException("로그인이 필요합니다.")
        }
        if (requestDTO.name.isEmpty()) {
            throw IllegalArgumentException("상품명을 입력해주세요.")
        }
        if (requestDTO.price <= 0) {
            throw IllegalArgumentException("상품 가격을 입력해주세요.")
        }
        if (requestDTO.stock <= 0) {
            throw IllegalArgumentException("상품 재고를 입력해주세요.")
        }
    }
}