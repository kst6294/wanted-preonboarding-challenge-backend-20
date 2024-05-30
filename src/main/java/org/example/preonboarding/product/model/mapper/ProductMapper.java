package org.example.preonboarding.product.model.mapper;

import org.example.preonboarding.product.model.entity.Product;
import org.example.preonboarding.product.model.payload.request.ProductCreateRequest;
import org.example.preonboarding.product.model.payload.response.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductResponse toProductResponse(Product product);

//    Product toProduct(ProductCreateRequest productCreateRequest);
}
