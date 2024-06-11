package com.example.hs.domain.goods.type;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToGoodsStatusConverter implements Converter<String, GoodsStatus> {
  @Override
  public GoodsStatus convert(String source) {
    try {
      return GoodsStatus.valueOf(source.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid GoodsStatus value: " + source);
    }
  }
}
