package com.wanted.preonboarding.auth.controller;


import com.wanted.preonboarding.auth.config.AuthConstants;
import com.wanted.preonboarding.auth.core.AuthToken;
import com.wanted.preonboarding.auth.dto.CreateAuthToken;
import com.wanted.preonboarding.auth.service.AuthTokenGenerateService;
import com.wanted.preonboarding.module.common.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class AuthController {

    private final AuthTokenGenerateService authTokenGenerateService;


    @PostMapping("/auth/authentication")
    public ResponseEntity<ApiResponse<Void>> getAccessToken(@RequestBody @Validated CreateAuthToken tokenRequest){
        AuthToken authToken = authTokenGenerateService.generateToken(tokenRequest.getEmail());
        return ResponseEntity.ok()
                .header(AuthConstants.AUTHORIZATION_HEADER, AuthConstants.BEARER_PREFIX + authToken.getToken())
                .body(ApiResponse.success(null, AuthConstants.ACCESS_TOKEN_ISSUED_MESSAGE));
    }


}
