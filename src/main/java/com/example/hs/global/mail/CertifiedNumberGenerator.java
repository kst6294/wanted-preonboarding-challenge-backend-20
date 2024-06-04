package com.example.hs.global.mail;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.springframework.stereotype.Component;

@Component
public class CertifiedNumberGenerator {
  public String createCertificationNumber(int numberLength) throws NoSuchAlgorithmException {
    String result;

    do {
      int num = SecureRandom.getInstanceStrong().nextInt(999999);
      result = String.valueOf(num);
    } while (result.length() != numberLength);

    return result;
  }
}
