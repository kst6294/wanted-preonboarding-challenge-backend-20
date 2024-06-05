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
    try {
      return GoodsStatus.valueOf(key);
    } catch (IllegalArgumentException e) {
      throw new IOException("Invalid value for GoodsStatus enum: " + key);
    }
  }
}