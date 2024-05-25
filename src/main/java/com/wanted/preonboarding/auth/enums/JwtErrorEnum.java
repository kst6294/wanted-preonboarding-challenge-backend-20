package com.wanted.preonboarding.auth.enums;

import com.wanted.preonboarding.module.common.enums.EnumType;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtErrorEnum implements EnumType {

    INVALID_SIGNATURE("Invalid JWT signature."),
    MALFORMED_TOKEN("Invalid JWT token."),
    EXPIRED_TOKEN("Expired JWT token."),
    UNSUPPORTED_TOKEN("Unsupported JWT token."),
    ILLEGAL_ARGUMENT("JWT token compact of handler are invalid.");

    private final String message;

    public static JwtErrorEnum from(Exception e) {
        if (e instanceof SecurityException) {
            return INVALID_SIGNATURE;
        } else if (e instanceof MalformedJwtException) {
            return MALFORMED_TOKEN;
        } else if (e instanceof ExpiredJwtException) {
            return EXPIRED_TOKEN;
        } else if (e instanceof UnsupportedJwtException) {
            return UNSUPPORTED_TOKEN;
        }
        return ILLEGAL_ARGUMENT;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getDescription() {
        return String.format("JWT ERROR NAME %s, ERROR MSG %s", name(), message);
    }
}
