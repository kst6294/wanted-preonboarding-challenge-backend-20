package com.example.hs.domain.goods.type;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

public class GoodsStatusDeserializer extends JsonDeserializer<GoodsStatus> {

  @Override
  public GoodsStatus deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
    String key = p.getText().toUpperCase();
    key = key.replaceAll("([a-z])([A-Z])", "$1_$2"); // camelCase를 snake_case로 변환
    key = key.replaceAll("\\s+", "_"); // 공백을 언더스코어로 변환
    try {
      return GoodsStatus.valueOf(key);
    } catch (IllegalArgumentException e) {
      throw new IOException("Invalid value for GoodsStatus enum: " + key);
    }
  }
}