package com.example.hs.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  INTERNAL_SERVER_ERROR(500,"내부 서버 오류가 발생했습니다."),
  REDIS_SERVER_ERROR(500, "REDIS 서버가 연결되지 않았습니다."),
  INVALID_REQUEST(400, "잘못된 요청입니다."),

  // 회원가입 관련
  NOT_EQUAL_PASSWORD(400, "비밀번호와 비밀번호확인이 같지 않습니다."),
  ALREADY_EXISTS_LOGIN_ID(400, "이미 존재하는 아이디 입니다. 다른 아이디로 회원가입 진행 가능합니다."),

  // 이메일 인증 관련
  EMAIL_NOT_FOUND(400, "이메일을 찾을 수 없습니다."),
  INVALID_CERTIFIED_NUMBER(400, "인증 번호가 유효하지 않습니다."),

  // 로그인 관련
  NOT_FOUND_MEMBER_LOGIN_ID(400, "해당하는 로그인 아이디를 찾을 수 없습니다. 아이디를 확인해주세요."),
  NOT_AUTHENTICATE_EMAIL(400, "회원가입 인증이 되지 않았습니다. 인증 후 사용 가능합니다."),
  NOT_MATCH_PASSWORD(400, "비밀번호가 틀렸습니다."),

  // 로그아웃 관련
  ALREADY_LOGOUT(400, "이미 로그아웃 한 사용자입니다."),

  // 토큰 재발급 관련
  INVALID_TOKEN_REISSUE_REQUEST(400, "올바르지 않은 재발급 요청입니다."),

  // 인증 관련
  UN_AUTHORIZATION(401, "인증이 되지 않았습니다."),
  NOT_MATCH_AUTHORIZATION(403, "접근 권한이 올바르지 않습니다."),

  // token 관련
  JWT_EXPIRED(401, "JWT가 만료되었습니다"),
  JWT_TOKEN_WRONG_TYPE(403,"JWT 토큰 형식에 문제가 생겼습니다"),
  JWT_TOKEN_MALFORMED(403, "토큰이 변조가 되었습니다"),
  NOT_FOUND_TOKEN(400, "토큰이 존재하지 않습니다."),
  INVALID_ACCESS_TOKEN(400, "유효하지 않은 Access Token 값입니다."),
  INVALID_REFRESH_TOKEN(400, "유효하지 않은 Refresh Token 값입니다."),
  NOT_MATCH_TOKEN_USER(400, "토큰의 사용자가 아닙니다."),
  NO_ROLE_TOKEN(400, "권한 정보가 없는 토큰입니다."),

  // 상품 관련
  NOT_FOUND_GOODS(400, "상품을 찾지 못했습니다.");

  private int httpCode;
  private String description;
}