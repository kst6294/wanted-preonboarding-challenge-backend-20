package market.market.domain.product.service;

import lombok.RequiredArgsConstructor;
import market.market.domain.product.domain.Product;
import market.market.domain.product.facade.ProductFacade;
import market.market.domain.product.presentation.dto.response.QueryProductListResponse;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryProductListService {
    private final ProductFacade productFacade;

    @Transactional(readOnly = true)
    public List<QueryProductListResponse> execute() {

        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<Product> products = productFacade.getProductAllById(sort);

        return products.stream()
                .map(product -> QueryProductListResponse.builder()
                        .productId(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .status(product.getStatus().getTitle())
                        .createdAt(product.getCreatedAt())
                        .build())
                .toList();
    }
}
