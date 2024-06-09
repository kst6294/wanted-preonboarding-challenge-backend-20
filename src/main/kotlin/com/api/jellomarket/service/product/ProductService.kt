package com.api.jellomarket.service.product

import com.api.jellomarket.domain.product.Product
import com.api.jellomarket.domain.product.ProductRepository
import com.api.jellomarket.domain.user.User
import com.api.jellomarket.dto.product.ProductCreateRequestDTO
import com.api.jellomarket.dto.product.ProductDetailDTO
import com.api.jellomarket.dto.product.ProductListDTO
import com.api.jellomarket.enums.product.ProductState
import com.api.jellomarket.utils.JelloUtils
import com.api.jellomarket.validation.ProductValidation
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Service
class ProductService(
    val productRepository: ProductRepository
) {
    /**
     * 상품 목록 조회
     * @return List<Product>
     */
    fun getProductList(): List<ProductListDTO> {
        val productList = productRepository.findAll()
        val resultList: MutableList<ProductListDTO> = ArrayList()
        for (product in productList) {
            resultList.add(
                ProductListDTO(
                    id = product.productId!!,
                    name = product.name,
                    price = product.price,
                    state = product.state,
                    stock = product.stock,
                    imageUrl = product.imageUrl?:"",
                    sellerId = product.sellerId!!,
                    createdAt = product.createdAt!!,
                )
            )
        }
        return resultList
    }

    /**
     * 상품 상세 조회
     * @param productId
     * @return Product
     */
    fun getProductDetail(productId: Long): ProductDetailDTO {
        val productOptional = productRepository.findById(productId)
        if (productOptional.isEmpty) {
            throw IllegalArgumentException("상품이 존재하지 않습니다.")
        }
        val product = productOptional.get()
        return ProductDetailDTO(
            id = product.productId!!,
            name = product.name,
            price = product.price,
            state = product.state.toString(),
            stock = product.stock,
            description = product.description?:"",
            imageUrl = product.imageUrl?:"",
            sellerId = product.sellerId!!,
            createdAt = product.createdAt!!,
            updatedAt = product.updatedAt?:""
        )
    }

    /**
     * 상품 등록
     * @param request
     * @return String
     */
    fun saveProduct(user: User?, request: ProductCreateRequestDTO): String {
        ProductValidation().validateProductCreateRequestDTO(user, request)
        val product = Product(
            name = request.name,
            price = request.price,
            state = ProductState.ON_SALE,
            stock = request.stock,
            description = request.description,
            imageUrl = request.imageUrl,
            sellerId = user?.userId!!,
            createdAt = JelloUtils().getCurrentDateTime(),
        )
        val savedProduct = productRepository.save(product)
        return savedProduct.name
    }
}