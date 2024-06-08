package market.market.domain.product.service;

import lombok.RequiredArgsConstructor;
import market.market.domain.product.domain.Product;
import market.market.domain.product.facade.ProductFacade;
import market.market.domain.product.presentation.dto.response.QueryProductDetailsResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryProductDetailService {
    private final ProductFacade productFacade;

    @Transactional(readOnly = true)
    public QueryProductDetailsResponse execute(Long productId) {
        Product product = productFacade.getProductById(productId);

        return QueryProductDetailsResponse.builder()
                .seller(product.getUser().getAccountId())
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .status(product.getStatus().getTitle())
                .createdAt(product.getCreatedAt()).build();
    }
}
