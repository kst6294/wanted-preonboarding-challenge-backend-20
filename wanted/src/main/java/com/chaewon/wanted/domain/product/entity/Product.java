package com.chaewon.wanted.domain.product.entity;

import com.chaewon.wanted.common.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Product extends BaseEntity {

}
