package com.wanted.preonboarding.module.product.repository;

import com.wanted.preonboarding.module.product.core.BaseSku;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.common.filter.ItemFilter;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductFindRepository {

    Optional<BaseSku> fetchBaseSku(long productId);

    Optional<Product> fetchProductEntity(long productId);

    Optional<Product> fetchProductEntity(long productId, String email);

    List<BaseSku> fetchBaseSkus(ItemFilter filter, Pageable pageable);

}
