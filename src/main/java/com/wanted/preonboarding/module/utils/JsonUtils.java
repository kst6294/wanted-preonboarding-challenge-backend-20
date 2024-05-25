package com.wanted.preonboarding.module.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wanted.preonboarding.module.exception.common.JsonSerializationException;

public class JsonUtils {
    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public static <T> T fromJson(String json, Class<T> valueType){
        try{
            return objectMapper.readValue(json, valueType);
        }catch (JsonProcessingException e){
            throw new JsonSerializationException(e.getMessage());
        }
    }

    public static <T> String toJson(T valueType){
        try{
            return objectMapper.writeValueAsString(valueType);
        }catch (JsonProcessingException e){
            throw new JsonSerializationException(e.getMessage());
        }
    }
}
