package com.wanted.challenge.product.repository;

import com.wanted.challenge.product.response.ProductPreviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

    Page<ProductPreviewResponse> retrieveProductsPreview(Pageable pageable);
}
