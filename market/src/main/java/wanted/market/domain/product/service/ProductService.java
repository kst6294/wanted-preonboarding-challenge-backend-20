package wanted.market.domain.product.service;

import wanted.market.domain.product.service.dto.request.ProductRegisterServiceRequest;
import wanted.market.domain.product.service.dto.response.ProductDetailResponse;
import wanted.market.domain.product.service.dto.response.ProductListResponse;

import java.util.List;

public interface ProductService {
    Long register(ProductRegisterServiceRequest request);

    List<ProductListResponse> findProductList(int page);

    ProductDetailResponse findProductDetail(Long id);

    List<ProductListResponse> findProductWithMember(String email);
}
