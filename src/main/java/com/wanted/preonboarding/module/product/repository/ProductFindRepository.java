package com.wanted.preonboarding.module.product.repository;

import com.wanted.preonboarding.module.product.core.BaseSku;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.product.filter.ItemFilter;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductFindRepository {

    Optional<BaseSku> fetchBaseSku(long productId);

    Optional<Product> fetchProductEntity(long productId);

    List<BaseSku> fetchBaseSkus(ItemFilter filter, Pageable pageable);

}
