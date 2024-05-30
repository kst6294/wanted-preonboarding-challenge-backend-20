package wanted.market.product.service

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import wanted.market.common.exception.ErrorCode
import wanted.market.member.service.MemberService
import wanted.market.order.OrderException
import wanted.market.order.entity.OrderStatus
import wanted.market.order.repository.OrderRepository
import wanted.market.product.dto.ProductDetailResponse
import wanted.market.product.dto.ProductResponse
import wanted.market.product.exception.ProductException
import wanted.market.product.dto.SaveProductRequest
import wanted.market.product.entity.Product
import wanted.market.product.entity.ProductStatus
import wanted.market.product.repository.ProductRepository

@Service
@Transactional
class ProductService(@Autowired private val productRepository: ProductRepository,
                     @Autowired private val memberService: MemberService,
                     @Autowired private val orderRepository: OrderRepository
) {
    fun findAllProducts(): List<ProductResponse> {
        return productRepository.findAll()
            .map { product-> ProductResponse.from(product)}
    }

    //상품 상세 조회 : 거래 내역도 조회되어야 함.
    fun findProductDetails(memberId: Long, productId: Long): ProductDetailResponse {
        val product = findProductById(productId)
        val order = orderRepository.findByBuyerIdAndProductId(memberId, productId)
            .orElseThrow{OrderException(ErrorCode.ORDER_NOT_FOUND)}
            return ProductDetailResponse.from(product, order, product.seller)
    }

    private fun findProductById(productId: Long): Product {
        val product = productRepository.findById(productId).orElseThrow {
            throw ProductException(ErrorCode.PRODUCT_NOT_FOUND)
        }
        return product
    }

    fun findProductOnSale(productId: Long): Product {
        val product = findProductById(productId)
        if (!product.isSaleProduct()) {
            throw ProductException(ErrorCode.PRODUCT_NOT_FOR_SALE)
        }
        return product
    }


    fun saveProduct(saveProductRequest: SaveProductRequest) {
        val member = memberService.findMember(saveProductRequest.memberId)
        val newProduct = Product(saveProductRequest.name, saveProductRequest.price, member)
        productRepository.save(newProduct)
    }

    fun findMyOrderProduct(memberId: Long): List<ProductResponse> {
        return orderRepository.findByBuyerIdAndOrderStatus(memberId, OrderStatus.APPROVED)
            .map { order -> ProductResponse.from(order.product) }
    }

    fun findReserveProductByBuyer(buyerId: Long): List<ProductResponse> {
        return orderRepository.findByBuyerIdAndOrderStatus(buyerId, OrderStatus.PENDING)
            .map { order -> ProductResponse.from(order.product) }
    }

    fun findReserveProductBySeller(sellerId: Long): List<ProductResponse> {
        return productRepository.findBySellerIdAndProductStatus(sellerId, ProductStatus.RESERVED)
            .map { product -> ProductResponse.from(product) }
    }
}