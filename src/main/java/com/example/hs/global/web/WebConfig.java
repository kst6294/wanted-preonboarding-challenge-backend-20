package com.example.hs.global.web;

import com.example.hs.domain.goods.type.StringToGoodsStatusConverter;
import com.example.hs.domain.transaction.type.StringToTransactionStatusConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(new StringToGoodsStatusConverter());
    registry.addConverter(new StringToTransactionStatusConverter());
  }
}
