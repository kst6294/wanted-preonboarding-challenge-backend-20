package com.wanted.challenge.product.repository;

import com.wanted.challenge.product.response.ProductPreviewResponse;
import com.wanted.challenge.product.response.PurchaseProductResponse;
import com.wanted.challenge.product.response.ReserveProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

    Page<ProductPreviewResponse> retrieveProductsPreview(Pageable pageable);

    Page<PurchaseProductResponse> retrievePurchaseProducts(Long buyerId, Pageable pageable);

    Page<ReserveProductResponse> retrieveReserveProducts(Long buyerId, Pageable pageable);
}
