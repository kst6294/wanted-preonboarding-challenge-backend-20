package org.example.preonboarding.product.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ProductType {
    UNCLASSIFIED("미분류"),
    ;
    public final String productTypeName;
}
