package com.example.hs.global.security;

public class SecurityConstant {

  public static final String[] AUTH_WHITELIST = {
      "/swagger-resources/**",
      "/swagger-ui.html",
      "/v2/api-docs",
      "/webjars/**",
      "/h2-console/**",
      "/auth/sign-up",
      "/auth/sign-in",
      "/mails/**"
  };
}
