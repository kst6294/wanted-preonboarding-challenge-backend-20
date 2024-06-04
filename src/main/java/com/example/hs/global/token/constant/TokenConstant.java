package com.example.hs.global.token.constant;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

public class TokenConstant {

  public static final String AUTHORITIES_KEY = "auth";
  public static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String BEARER_TYPE = "Bearer ";

  public static final SecretKey key =  Keys.secretKeyFor(SignatureAlgorithm.HS512);
//  public static final long  ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60; // 1 hour
//  public static final long  REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7 day

  // TODO: API 테스트시 짧은 유효기간. 삭제 예정
  public static final long  ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 3; // 3 minute (test)
  public static final long  REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 15;  //  15 minute (test)

}
