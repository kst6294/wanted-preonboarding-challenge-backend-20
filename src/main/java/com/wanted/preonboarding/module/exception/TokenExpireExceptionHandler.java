package com.wanted.preonboarding.module.exception;

import com.wanted.preonboarding.auth.AuthTokenProvider;
import com.wanted.preonboarding.auth.config.AuthConstants;
import com.wanted.preonboarding.auth.core.AuthToken;
import com.wanted.preonboarding.auth.core.JwtAuthToken;
import com.wanted.preonboarding.auth.service.AuthTokenGenerateService;
import com.wanted.preonboarding.auth.service.TokenFetchService;
import com.wanted.preonboarding.module.common.payload.ApiResponse;
import com.wanted.preonboarding.module.common.payload.ErrorResponse;
import com.wanted.preonboarding.module.exception.auth.CustomExpiredJwtException;
import com.wanted.preonboarding.module.exception.auth.UnAuthorizationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
@RequiredArgsConstructor
public class TokenExpireExceptionHandler {

    private final TokenFetchService tokenFetchService;
    private final AuthTokenGenerateService authTokenGenerateService;


    private static final String REFRESH_TOKEN_ERR_MSG = "Refresh token is invalid or expired";

    @ExceptionHandler(CustomExpiredJwtException.class)
    public ResponseEntity<?> handleExpiredJwtException(CustomExpiredJwtException e, HttpServletRequest request) {

        Claims claims = e.getClaims();
        Optional<AuthToken> authToken = tokenFetchService.fetchToken(claims.getSubject());
        if(authToken.isPresent()) {
            AuthToken refreshToken = authToken.get();
            AuthToken newAuthToken = authTokenGenerateService.generateToken(refreshToken.getSubject());
            return ResponseEntity.ok()
                    .header(AuthConstants.AUTHORIZATION_HEADER, AuthConstants.BEARER_PREFIX + newAuthToken.getToken())
                    .body(ApiResponse.success(null, AuthConstants.REFRESH_TOKEN_ISSUED_MESSAGE));
        }

        throw new UnAuthorizationException(REFRESH_TOKEN_ERR_MSG);
    }


}
