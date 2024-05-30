package com.wanted.challenge.product.repository;

import com.wanted.challenge.product.response.ProductPreviewResponse;
import com.wanted.challenge.product.response.PurchaseProductResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

    Page<ProductPreviewResponse> retrieveProductsPreview(Pageable pageable);

    List<PurchaseProductResponse> retrievePurchaseProducts(Long buyerId);
}
