package com.wanted.preonboarding.auth.service;

import com.wanted.preonboarding.auth.core.AuthToken;

import java.util.Optional;

public interface TokenFetchService {

    Optional<AuthToken> fetchToken(String email);
}
