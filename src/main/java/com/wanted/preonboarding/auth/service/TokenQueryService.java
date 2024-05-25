package com.wanted.preonboarding.auth.service;

import com.wanted.preonboarding.auth.core.AuthToken;

public interface TokenQueryService {

    void saveToken(AuthToken token);

}
