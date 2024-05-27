package com.wanted.preonboarding.module.product.service;


import com.wanted.preonboarding.module.common.dto.CustomSlice;
import com.wanted.preonboarding.module.exception.product.NotFoundProductException;
import com.wanted.preonboarding.module.product.core.BaseSku;
import com.wanted.preonboarding.module.product.core.Sku;
import com.wanted.preonboarding.module.product.filter.ItemFilter;
import com.wanted.preonboarding.module.product.mapper.ProductSliceMapper;
import com.wanted.preonboarding.module.product.repository.ProductFindRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ProductFetchServiceImpl implements ProductFetchService{

    private final ProductSliceMapper productSliceMapper;
    private final ProductFindRepository productFindRepository;

    @Override
    public Sku fetchProduct(long productId) {
        return productFindRepository.fetchBaseSku(productId)
                .orElseThrow(() -> new NotFoundProductException(productId));
    }

    @Override
    public CustomSlice<Sku> fetchProducts(ItemFilter filter, Pageable pageable) {
        List<BaseSku> baseSkus = productFindRepository.fetchBaseSkus(filter, pageable);
        List<Sku> skus = baseSkus.stream().map(sku -> (Sku) sku).collect(Collectors.toList());
        return productSliceMapper.toSlice(skus, pageable, filter);
    }

}
