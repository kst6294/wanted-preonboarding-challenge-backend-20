package org.example.preonboarding.product.service;

import org.example.preonboarding.product.exception.ProductNotFoundException;
import org.example.preonboarding.product.model.payload.request.ProductCreateRequest;
import org.example.preonboarding.product.model.payload.response.ProductResponse;

import java.util.List;


public interface ProductService {

    List<ProductResponse> getProducts();

    List<ProductResponse> getProductsByUser();

    ProductResponse getProductById(Long productId);

    ProductResponse createProduct(ProductCreateRequest productCreateRequest);

    ProductResponse updateProduct(Long productId, ProductCreateRequest productCreateRequest);

    ProductResponse deleteProduct(Long productId) throws ProductNotFoundException;

}
