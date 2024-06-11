package com.example.hs.domain.transaction.type;

import org.springframework.core.convert.converter.Converter;
import com.example.hs.domain.transaction.type.TransactionStatus;

public class StringToTransactionStatusConverter implements Converter<String, TransactionStatus> {
  @Override
  public TransactionStatus convert(String source) {
    try {
      return TransactionStatus.valueOf(source.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid TransactionStatus: " + source);
    }
  }
}
